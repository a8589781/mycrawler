package cn.my.crawler.pojo;

import java.util.Date;

/**
 * 
 * 说明：爬虫:it橘子
 * 
 * @author 刘品呈
 * @version 1.0
 * @date 2016年4月21日
 */
public class ItOrange {

    /**
     * id
     */
    private Long id;

    private Date investmentTime;

    private String title;

    private String url;

    private String image;

    private String round;

    private String money;

    private String investorset;

    public ItOrange() {
    }

    public ItOrange(Long id, Date time, String title, String image, String round, String money,
            String investorset) {
        super();
        this.id = id;
        this.investmentTime = time;
        this.title = title;
        this.image = image;
        this.round = round;
        this.money = money;
        this.investorset = investorset;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInvestmentTime() {
        return investmentTime;
    }

    public void setInvestmentTime(Date investmentTime) {
        this.investmentTime = investmentTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getInvestorset() {
        return investorset;
    }

    public void setInvestorset(String investorset) {
        this.investorset = investorset;
    }

}
