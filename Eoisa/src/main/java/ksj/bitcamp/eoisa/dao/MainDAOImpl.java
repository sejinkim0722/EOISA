package ksj.bitcamp.eoisa.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ksj.bitcamp.eoisa.dto.MainDTO;

@Repository
public class MainDAOImpl implements MainDAO {
	@Autowired
	private SqlSession sqlSession;
	private String ns_main = "ksj.bitcamp.eoisa.dto.MainDTO";

	@Override
	public void crawling_algumon(MainDTO dto) {
		String url_algumon = "https://algumon.com/more/";
		Elements isended = null, goods_pic = null, site_buy = null, site_src = null, goods_title = null, price = null, deliever_fee = null, writetime = null, dealinfos = null;
		int max = sqlSession.selectOne("calc_maxpage");

		for (int p = 0; p < max + 1; p++) {
			long timelaps_start = System.currentTimeMillis();
			try {
				Document doc = Jsoup.connect(url_algumon + p + "?types=ended").maxBodySize(0).get();

				Elements pbody = doc.select("li.left.clearfix.post-li");
				for (Element el : pbody) {
					isended = el.select("span.label.end");
					goods_title = el.select("p > span.item-name > a");
					Response res = Jsoup.connect("https://algumon.com" + goods_title.attr("href")).followRedirects(true).execute();
					goods_pic = el.select("div.product-img-box > a > img");
					price = el.select("p > small.product-price");
					deliever_fee = el.select("p > small.product-shipping-fee");
					writetime = el.select("small.label-time");
					site_src = el.select("span.label.site");
					site_buy = el.select("span.label.shop > a");

					String region = "국내";
					if (price.text().matches("([$]|[£]|[¥]|[€]).*")) region = "해외";

					dealinfos = el.select("span.deal-info");
					String[] temp = dealinfos.text().split(" ");
					String[] split = { "0", "0", "0" };
					for (int i = 0; i < temp.length; i++) split[i] = temp[i];
					if (temp[0].trim().equals("")) split[0] = "0";

					String price_deal = price.text();
					String price_naver = getNaverlprice(goods_title.text());
					if (price_deal.contains("원") && !price_naver.equals("정보 없음") && region.equals("국내")) {
						int temp_price = Integer.parseInt(price_deal.trim().replaceAll("[^0-9]", ""));
						int temp_price_naver = Integer.parseInt(price_naver.trim().replaceAll("[^0-9]", ""));
						String merit = String.format("%,d", (temp_price_naver - temp_price));
						dto.setMerit(merit);
					} else {
						dto.setMerit("");
					}

					dto.setSite_src(site_src.first().text());
					dto.setUrl_src(res.url().toString());
					dto.setSite_buy(site_buy.text());
					dto.setCategory("unknown");
					dto.setRegion(region);
					dto.setGoods_title(goods_title.text());
					dto.setGoods_pic(goods_pic.attr("src"));
					dto.setPrice(price_deal);
					dto.setPrice_naver(price_naver);
					dto.setDeliever_fee(deliever_fee.text());
					dto.setWritetime(writetime.first().text());
					dto.setIsended(isended.text());
					dto.setReplycount_src(Integer.parseInt(split[0]));
					dto.setLikeit_src(Integer.parseInt(split[1]));
					dto.setDislikeit_src(Integer.parseInt(split[2]));

					sqlSession.update(ns_main + ".crawling", dto); // DEALINFO Upsert
				}
				long timelaps_end = System.currentTimeMillis();
				System.out.println("Page " + p + " Crawling Complete (" + ((timelaps_end - timelaps_start) / 1000) + " sec)");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static final String clientId = "";
	private static final String clientSecret = "";

	private String getNaverlprice(String param) {
		int result;
		try {
			String query = URLEncoder.encode(param.replaceAll("[\\p{S}\\p{P}]+", "")
					.replaceAll("(끌올|할인|청구시|청구|가성비|품절|배송비|배송|합배용|합배|관세|관부가세|적용|적용시|쿠폰|포인트|무료|특가|추가|강추|최대|최소|NH|신한|KB|국민|스마일클럽|유니온페이)", ""), "UTF-8");
			URL url = new URL("https://openapi.naver.com/v1/search/shop.json?query=" + query);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			JsonElement jelement = new JsonParser().parse(br);
			JsonObject jobject = jelement.getAsJsonObject();
			JsonArray jarray = jobject.getAsJsonArray("items");
			jobject = jarray.get(0).getAsJsonObject();
			result = jobject.get("lprice").getAsInt();

			br.close();
			con.disconnect();

			return String.format("%,d", result);
		} catch (Exception e) {
			return "정보 없음";
		}
	}

	@Override
	public int pagination(String title, int pageNum) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		int count = sqlSession.selectOne(ns_main + ".dealcount", params);

		return getTotalPage(count, pageNum);
	}

	@Override
	public int searchPagination(String keyword, int pageNum) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("keyword", "%" + keyword + "%");
		int count = sqlSession.selectOne(ns_main + ".dealcount", params);

		return getTotalPage(count, pageNum);
	}

	@Override
	public int filterPagination(int pageNum, MultiValueMap<String, List<String>> filters) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("region", filters.get("region"));
		params.put("site", filters.get("site"));
		params.put("shop", filters.get("shop"));
		params.put("isended", filters.get("isended"));
		int count = sqlSession.selectOne(ns_main + ".dealcount", params);

		return getTotalPage(count, pageNum);
	}

	private int getTotalPage(int count, int pageNum) {
		int totalPage = count / 10;
		if (count % 10 > 0)
			totalPage++;
		if (totalPage < pageNum)
			pageNum = totalPage;

		return totalPage;
	}

	@Override
	public List<MainDTO> deal(int pageNum) {
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("startRownum", (pageNum - 1) * 10);
		params.put("endRownum", ((pageNum - 1) * 10) + 10);

		return sqlSession.selectList(ns_main + ".dealpage", params);
	}

	@Override
	public List<MainDTO> filter(int pageNum, MultiValueMap<String, List<String>> filters) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		List<String> rownum = Arrays.asList(Integer.toString((pageNum - 1) * 10), Integer.toString(((pageNum - 1) * 10) + 10));
		params.put("region", filters.get("region"));
		params.put("site", filters.get("site"));
		params.put("shop", filters.get("shop"));
		params.put("isended", filters.get("isended"));
		params.put("rownum", rownum);

		return sqlSession.selectList(ns_main + ".dealpage", params);
	}

	@Override
	public List<MainDTO> rankpage() {
		return sqlSession.selectList(ns_main + ".rankpage");
	}

	@Override
	public List<Map<String, Integer>> ranking() {
		return sqlSession.selectList(ns_main + ".ranking");
	}

	@Override
	public List<MainDTO> search(String keyword, int pageNum) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("keyword", "%" + keyword + "%");
		params.put("startRownum", Integer.toString((pageNum - 1) * 10));
		params.put("endRownum", Integer.toString(((pageNum - 1) * 10) + 10));

		return sqlSession.selectList(ns_main + ".dealpage", params);
	}

	@Override
	public List<MainDTO> theme(String title, int pageNum) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		params.put("startRownum", Integer.toString((pageNum - 1) * 10));
		params.put("endRownum", Integer.toString(((pageNum - 1) * 10) + 10));

		return sqlSession.selectList(ns_main + ".dealpage", params);
	}

	@Override
	public int manageWishlist(MainDTO dto) {
		if ((int) sqlSession.selectOne(ns_main + ".wishlist_check", dto) != 0) {
			return sqlSession.delete(ns_main + ".wishlist_delete", dto);
		}
		if ((int) sqlSession.selectOne(ns_main + ".wishlist_max", dto) == 10) {
			return 0;
		} else {
			return sqlSession.insert(ns_main + ".wishlist_insert", dto);
		}
	}

	@Override
	public List<MainDTO> wishlist(String username) {
		if (!username.equals("anonymousUser")) {
			return sqlSession.selectList(ns_main + ".wishlist", username);
		} else {
			return null;
		}
	}

	@Override
	public String link(int dealno) {
		sqlSession.update(ns_main + ".viewcount_up", dealno);

		return sqlSession.selectOne(ns_main + ".url_src", dealno);
	}
}