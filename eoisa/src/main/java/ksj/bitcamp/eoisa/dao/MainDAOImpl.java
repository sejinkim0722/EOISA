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
	
	private static final String NS_MAIN = "ksj.bitcamp.eoisa.dto.MainDTO";
	private static final String TARGET_URL = "https://algumon.com";

	@Override
	public void crawling(MainDTO dto) {
		int max = sqlSession.selectOne(NS_MAIN + "maxPage");

		for (int page = 0; page < max + 1; page++) {
			try {
				long timelapsStart = System.currentTimeMillis();

				Document doc = Jsoup.connect(TARGET_URL + "/more/" + page + "?types=ended").maxBodySize(0).get();
				Elements body = doc.select("li.left.clearfix.post-li");
				for (Element el : body) {
					dto.setIsended(el.select("span.label.end").text());
					dto.setGoods_title(el.select("p > span.item-name > a").text());
					dto.setGoods_pic(el.select("div.product-img-box > a > img").attr("src"));
					dto.setUrl_src(Jsoup.connect(TARGET_URL + el.select("p > span.item-name > a").attr("href"))
										.followRedirects(true)
										.execute()
										.url()
										.toString());
					dto.setWritetime(el.select("small.label-time").first().text());
					dto.setSite_buy(el.select("span.label.shop > a").text());
					dto.setSite_src(el.select("span.label.site").first().text());
					dto.setRegion(el.select("p > small.product-price").text().matches("([$]|[£]|[¥]|[€]).*") ? "국내" : "해외");
					dto.setCategory("unknown");

					Elements dealinfo = el.select("span.deal-info");
					String[] temp = dealinfo.text().split(" ");
					String[] split = { "0", "0", "0" };
					for (int i = 0; i < temp.length; i++) split[i] = temp[i];
					if(temp[0].trim().equals("")) split[0] = "0";
					dto.setReplycount_src(Integer.parseInt(split[0]));
					dto.setLikeit_src(Integer.parseInt(split[1]));
					dto.setDislikeit_src(Integer.parseInt(split[2]));

					String dealPrice = el.select("p > small.product-price").text();
					String naverPrice = getNaverPrice(el.select("p > span.item-name > a").text());
					if(dealPrice.contains("원") && !naverPrice.equals("정보 없음")) {
						int tempDealPrice = Integer.parseInt(dealPrice.trim().replaceAll("[^0-9]", ""));
						int tempNaverPrice = Integer.parseInt(naverPrice.trim().replaceAll("[^0-9]", ""));
						dto.setMerit(String.format("%,d", (tempNaverPrice - tempDealPrice)));
					} else {
						dto.setMerit("");
					}
					dto.setPrice(dealPrice);
					dto.setPrice_naver(naverPrice);
					dto.setDeliever_fee(el.select("p > small.product-shipping-fee").text());
					
					sqlSession.update(NS_MAIN + ".crawling", dto); // DEALINFO Upsert
				}
				long timelapsEnd = System.currentTimeMillis();
				
				System.out.println("Crawling Complete : Page " + page + "(" + (timelapsEnd - timelapsStart) + " ms)");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static final String CLIENT_ID = "";
	private static final String CLIENT_SECRET = "";

	private String getNaverPrice(String goodsTitle) {
		try {
			String query = URLEncoder.encode(goodsTitle.replaceAll("[\\p{S}\\p{P}]+", "")
					.replaceAll("(끌올|할인|청구시|청구|가성비|품절|배송비|배송|합배용|합배|관세|관부가세|적용|적용시|쿠폰|포인트|무료|특가|추가|강추|최대|최소|NH|신한|KB|국민|스마일클럽|유니온페이)", ""), "UTF-8");
			URL url = new URL("https://openapi.naver.com/v1/search/shop.json?query=" + query);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", CLIENT_ID);
			con.setRequestProperty("X-Naver-Client-Secret", CLIENT_SECRET);
			
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
			int result = jobject.get("lprice").getAsInt();

			br.close();
			con.disconnect();

			return String.format("%,d", result);
		} catch (Exception e) {
			return "정보 없음";
		}
	}

	@Override
	public int paging(String title, int pageNum) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		int count = sqlSession.selectOne(NS_MAIN + ".dealCount", params);

		return getTotalPage(count, pageNum);
	}

	@Override
	public int searchPaging(String keyword, int pageNum) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("keyword", "%" + keyword + "%");
		int count = sqlSession.selectOne(NS_MAIN + ".dealCount", params);

		return getTotalPage(count, pageNum);
	}

	@Override
	public int filterPaging(int pageNum, MultiValueMap<String, List<String>> filters) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("region", filters.get("region"));
		params.put("site", filters.get("site"));
		params.put("shop", filters.get("shop"));
		params.put("isended", filters.get("isended"));
		int count = sqlSession.selectOne(NS_MAIN + ".dealCount", params);

		return getTotalPage(count, pageNum);
	}

	private int getTotalPage(int count, int pageNum) {
		int totalPage = count / 10;
		if (count % 10 > 0) totalPage++;
		if (totalPage < pageNum) pageNum = totalPage;

		return totalPage;
	}

	@Override
	public List<MainDTO> getDealPage(int pageNum) {
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("startRownum", (pageNum - 1) * 10);
		params.put("endRownum", ((pageNum - 1) * 10) + 10);

		return sqlSession.selectList(NS_MAIN + ".dealPage", params);
	}

	@Override
	public List<MainDTO> getFilteredPage(int pageNum, MultiValueMap<String, List<String>> filters) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		List<String> rownum = Arrays.asList(Integer.toString((pageNum - 1) * 10), Integer.toString(((pageNum - 1) * 10) + 10));
		params.put("region", filters.get("region"));
		params.put("site", filters.get("site"));
		params.put("shop", filters.get("shop"));
		params.put("isended", filters.get("isended"));
		params.put("rownum", rownum);

		return sqlSession.selectList(NS_MAIN + ".dealPage", params);
	}

	@Override
	public List<MainDTO> getRankPage() {
		return sqlSession.selectList(NS_MAIN + ".rankPage");
	}

	@Override
	public List<Map<String, Integer>> getRanking() {
		return sqlSession.selectList(NS_MAIN + ".ranking");
	}

	@Override
	public List<MainDTO> getSearchResult(String keyword, int pageNum) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("keyword", "%" + keyword + "%");
		params.put("startRownum", Integer.toString((pageNum - 1) * 10));
		params.put("endRownum", Integer.toString(((pageNum - 1) * 10) + 10));

		return sqlSession.selectList(NS_MAIN + ".dealPage", params);
	}

	@Override
	public List<MainDTO> getThemePage(String title, int pageNum) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		params.put("startRownum", Integer.toString((pageNum - 1) * 10));
		params.put("endRownum", Integer.toString(((pageNum - 1) * 10) + 10));

		return sqlSession.selectList(NS_MAIN + ".dealPage", params);
	}

	@Override
	public int manageWishlist(MainDTO dto) {
		if((int)sqlSession.selectOne(NS_MAIN + ".wishlistCheck", dto) != 0) return sqlSession.delete(NS_MAIN + ".wishlistDelete", dto);
		
		if((int)sqlSession.selectOne(NS_MAIN + ".wishlistMax", dto) == 10) {
			return 0;
		} else {
			return sqlSession.insert(NS_MAIN + ".wishlistInsert", dto);
		}
	}

	@Override
	public List<MainDTO> getWishlist(String username) {
		if (!username.equals("anonymousUser")) {
			return sqlSession.selectList(NS_MAIN + ".wishlist", username);
		} else {
			return null;
		}
	}

	@Override
	public String viewcountIncrease(int dealno) {
		sqlSession.update(NS_MAIN + ".viewcountUp", dealno);

		return sqlSession.selectOne(NS_MAIN + ".srcURL", dealno);
	}
	
}