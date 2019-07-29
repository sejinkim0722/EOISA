package ksj.bitcamp.eoisa.dto;

public class MainDTO {
	
	private int dealno;
	private String username;
	private String site_src;
	private String url_src;
	private String site_buy;
	private String url_buy;
	private String category;
	private String region;
	private String goods_title;
	private String goods_pic;
	private String price;
	private String price_naver;
	private String merit;
	private String deliever_fee;
	private String writetime;
	private String isended;
	private int replycount;
	private int replycount_src;
	private int likeit;
	private int likeit_src;
	private int dislikeit_src;
	private int viewcount;
	private int rk;
	
	public MainDTO() {}

	public MainDTO(int dealno, String username, String site_src, String url_src, String site_buy, String url_buy,
			String category, String region, String goods_title, String goods_pic, String price, String price_naver,
			String merit, String deliever_fee, String writetime, String isended, int replycount, int replycount_src,
			int likeit, int likeit_src, int dislikeit_src, int viewcount, int rk) {
		this.dealno = dealno;
		this.username = username;
		this.site_src = site_src;
		this.url_src = url_src;
		this.site_buy = site_buy;
		this.url_buy = url_buy;
		this.category = category;
		this.region = region;
		this.goods_title = goods_title;
		this.goods_pic = goods_pic;
		this.price = price;
		this.price_naver = price_naver;
		this.merit = merit;
		this.deliever_fee = deliever_fee;
		this.writetime = writetime;
		this.isended = isended;
		this.replycount = replycount;
		this.replycount_src = replycount_src;
		this.likeit = likeit;
		this.likeit_src = likeit_src;
		this.dislikeit_src = dislikeit_src;
		this.viewcount = viewcount;
		this.rk = rk;
	}

	public int getDealno() {
		return dealno;
	}

	public void setDealno(int dealno) {
		this.dealno = dealno;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSite_src() {
		return site_src;
	}

	public void setSite_src(String site_src) {
		this.site_src = site_src;
	}

	public String getUrl_src() {
		return url_src;
	}

	public void setUrl_src(String url_src) {
		this.url_src = url_src;
	}

	public String getSite_buy() {
		return site_buy;
	}

	public void setSite_buy(String site_buy) {
		this.site_buy = site_buy;
	}

	public String getUrl_buy() {
		return url_buy;
	}

	public void setUrl_buy(String url_buy) {
		this.url_buy = url_buy;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getGoods_title() {
		return goods_title;
	}

	public void setGoods_title(String goods_title) {
		this.goods_title = goods_title;
	}

	public String getGoods_pic() {
		return goods_pic;
	}

	public void setGoods_pic(String goods_pic) {
		this.goods_pic = goods_pic;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice_naver() {
		return price_naver;
	}

	public void setPrice_naver(String price_naver) {
		this.price_naver = price_naver;
	}

	public String getMerit() {
		return merit;
	}

	public void setMerit(String merit) {
		this.merit = merit;
	}

	public String getDeliever_fee() {
		return deliever_fee;
	}

	public void setDeliever_fee(String deliever_fee) {
		this.deliever_fee = deliever_fee;
	}

	public String getWritetime() {
		return writetime;
	}

	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}

	public String getIsended() {
		return isended;
	}

	public void setIsended(String isended) {
		this.isended = isended;
	}

	public int getReplycount() {
		return replycount;
	}

	public void setReplycount(int replycount) {
		this.replycount = replycount;
	}

	public int getReplycount_src() {
		return replycount_src;
	}

	public void setReplycount_src(int replycount_src) {
		this.replycount_src = replycount_src;
	}

	public int getLikeit() {
		return likeit;
	}

	public void setLikeit(int likeit) {
		this.likeit = likeit;
	}

	public int getLikeit_src() {
		return likeit_src;
	}

	public void setLikeit_src(int likeit_src) {
		this.likeit_src = likeit_src;
	}

	public int getDislikeit_src() {
		return dislikeit_src;
	}

	public void setDislikeit_src(int dislikeit_src) {
		this.dislikeit_src = dislikeit_src;
	}

	public int getViewcount() {
		return viewcount;
	}

	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}

	public int getRk() {
		return rk;
	}

	public void setRk(int rk) {
		this.rk = rk;
	}
	
}