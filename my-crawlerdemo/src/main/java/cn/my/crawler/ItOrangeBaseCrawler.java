package cn.my.crawler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.my.crawler.mapper.ItOrangeMapper;
import cn.my.crawler.pojo.ItOrange;
import cn.my.crawler.service.HttpService;
import cn.my.crawler.thread.ThreadPool;

public abstract class ItOrangeBaseCrawler implements Crawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItOrangeBaseCrawler.class);

    private HttpService httpService;

    private ItOrangeMapper itOrangeMapper;

    /**
     * 开始抓取数据
     */
    public void start() {
        Integer totalPage = getTotalPage();
        // 分页抓取
        for (int i = 1; i <= totalPage; i++) {
            LOGGER.info("当前第{}页，总共{}页。", i, totalPage);
            Collection<ItOrange> itOranges = doStart(i);
            if (itOranges == null) {
                LOGGER.info("抓取到 0 条数据");
                continue;
            }
            LOGGER.info("抓取到{}条数据", itOranges.size());

            // 下载图片，将文档中的图片地址替换成自己的url
            Map<String, String> urlMapping = new HashMap<String, String>();
            for (ItOrange itOrange : itOranges) {
                // 下载商品的图片
                String newName = StringUtils.replace(UUID.randomUUID().toString(), "-", "") + "."
                        + StringUtils.substringAfterLast(itOrange.getImage(), ".");
                urlMapping.put(itOrange.getImage(), newName);

                itOrange.setImage(newName);
            }
            // 启动新线程下载图片
            ThreadPool.runInThread(new ImageDownloadCrawler(urlMapping));
            // 保存爬虫数据
            saveDataToDB(itOranges);
            LOGGER.info("将数据保存到数据库完成 ({})!", itOranges.size());
        }
    }

    /**
     * 
     * 说明：数据保存到数据库
     * 
     * @param ItOranges
     * @author 刘品呈
     * @time：2016年4月21日 上午11:06:30
     */
    private void saveDataToDB(Collection<ItOrange> itOranges) {
        itOrangeMapper.saveItOranges(itOranges);
    }

    public String doGet(String url) throws Exception {
        return this.httpService.doGet(url);
    }

    public String doGet(String url, String encode) throws Exception {
        return this.httpService.doGet(url, encode);
    }

    /**
     * 抓取获取到集合
     * 
     * @param page
     * @return
     */
    protected Collection<ItOrange> doStart(Integer page) {
        String url = getPageUrl(page);
        LOGGER.info(" URL is " + url);
        String html = null;
        try {
            html = this.httpService.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (html == null) {
            return null;
        }
        return doParser(html);
    }

    /**
     * 解析html，生成ItOrange对象
     * 
     * @param html
     * @return
     */
    protected abstract Collection<ItOrange> doParser(String html);

    /**
     * 根据页数得到url
     * 
     * @param page
     * @return
     */
    protected abstract String getPageUrl(Integer page);

    /**
     * 获取总页数
     * 
     * @return
     */
    protected abstract Integer getTotalPage();

    @Override
    public void run() {
        start();
    }

    public void setHttpService(HttpService httpService) {
        this.httpService = httpService;
    }

    public void setItOrangeMapper(ItOrangeMapper itOrangeMapper) {
        this.itOrangeMapper = itOrangeMapper;
    }

}
