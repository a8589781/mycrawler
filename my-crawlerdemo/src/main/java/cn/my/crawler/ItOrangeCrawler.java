package cn.my.crawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.my.crawler.pojo.ItOrange;

public class ItOrangeCrawler extends ItOrangeBaseCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItOrangeCrawler.class);

    private String baseUrl;

    /**
     * 
     * @param baseUrl like https://www.itjuzi.com/investevents?page={page}
     */
    public ItOrangeCrawler(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    protected Collection<ItOrange> doParser(String html) {
        Document document = Jsoup.parse(html);// 解析列表页面
        Elements lis = document.select(".list-main-eventset li");// 获取全部的li
        Map<String, ItOrange> map = new HashMap<String, ItOrange>();
        for (Element li : lis) {
            // 获取li下的div ,再通过div获取下面的相关属性 ,封装到对象中
            ItOrange itOrange = new ItOrange();
            Elements select = li.select(".title");
            // 第一次进来的抬头信息
            if (select.size() == 0) {
                continue;
            }
            String url = li.select(".title a").attr("href");
            String id = url.substring(url.lastIndexOf("/") + 1);
            // li中两个同样的round
            Element div = li.child(0);
            String time = div.select(".round").text();
            String title = li.select(".title a span").text();
            String image = li.select(".incicon img").attr("src");
            String round = li.select(".round a span").text();
            String money = li.select(".fina").text();
            String investorset = li.select(".date a").text();

            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            GregorianCalendar calendar = new GregorianCalendar();
            try {
                calendar.setTime(format.parse(time));
            } catch (ParseException e) {
                e.printStackTrace();
                LOGGER.error("时间转换出错", e);
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            Date date = calendar.getTime();

            itOrange.setId(Long.valueOf(id));
            itOrange.setInvestmentTime(date);
            itOrange.setTitle(title);
            itOrange.setUrl(url);
            itOrange.setImage(image);
            itOrange.setRound(round);
            itOrange.setMoney(money);
            itOrange.setInvestorset(investorset);
            map.put(id, itOrange);
        }
        return map.values();
    }

    @Override
    protected String getPageUrl(Integer page) {
        return StringUtils.replace(this.baseUrl, "{page}", page + "");
    }

    @Override
    protected Integer getTotalPage() {
        String html = null;
        try {
            html = super.doGet(getPageUrl(1));
        } catch (Exception e) {
            LOGGER.error("getTotalPage error !", e);
            return 0;
        }
        Document document = Jsoup.parse(html);
        String pageHtml = document.select(".for-sec-bottom :last-child").attr("data-ci-pagination-page");
        String[] no = pageHtml.split("\\D+");// 将数字提取出来

        return Integer.valueOf(no[0]);
    }
}
