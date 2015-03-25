package MapReduce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MapReduce.PostProcessQ3.Response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ReducerQ3 {

	public static void main(String args[]) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in, "UTF-8"));
			String line;
			String prevX = "", x = "";
			Map<Long, Integer> plus = new HashMap<Long, Integer>();
			Map<Long, Integer> minus = new HashMap<Long, Integer>();
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.equals(""))
					continue;
				String[] ids = line.split("\t");
				// x retweets y, so x minus y ---> it also means y plus x
				x = ids[0];
				char relation = ids[1].charAt(0);
				long y = Long.parseLong(ids[1].substring(1));
				if (!x.equals(prevX)) {
					printLine(prevX, plus, minus);
					prevX = x;
					plus = new HashMap<Long, Integer>();
					minus = new HashMap<Long, Integer>();
				}
				if (relation == '+')
					add(plus, y);
				else if (relation == '-')
					add(minus, y);

			}
			// the last element
			printLine(x, plus, minus);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception en) {
			en.printStackTrace();
		}
	}

	private static void add(Map<Long, Integer> map, long y) {
		if (map.containsKey(y)) {
			map.put(y, map.get(y) + 1);
		} else
			map.put(y, 1);
	}

	private static void printLine(String x, Map<Long, Integer> plus,
			Map<Long, Integer> minus) {
		List<Response> listX = new ArrayList<Response>();
		for (Map.Entry<Long, Integer> entry : plus.entrySet()) {
			Long key = entry.getKey();
			Integer value = entry.getValue();
			if (minus.containsKey(key)) {
				listX.add(new Response('*', value + minus.get(key), key));
				minus.remove(key);
			} else {
				listX.add(new Response('+', value, key));
			}

		}
		for (Map.Entry<Long, Integer> entry : minus.entrySet()) {
			Long key = entry.getKey();
			Integer value = entry.getValue();
			listX.add(new Response('-', value, key));
		}
		Collections.sort(listX);
		// print ele
		StringBuffer ans = new StringBuffer(String.valueOf(x) + "\t");
		for (Response res : listX) {
			ans.append(res.toString());
		}
		// terminated by \n
		System.out.println(ans);

	}

	static class Response implements Comparable<Response> {
		char type;
		int count;
		long userid;

		Response(char type, int count, long userid) {
			this.type = type;
			this.count = count;
			this.userid = userid;
		}

		public String toString() {
			return this.type + "," + this.count + "," + this.userid + '_';
		}

		public int compareTo(Response that) {
			if (this.type != that.type) {
				return this.type - that.type;
			} else {
				if (this.count != that.count) {
					return that.count - this.count;
				}
			}
			return (int) (this.userid - that.userid);
		}
	}

}
