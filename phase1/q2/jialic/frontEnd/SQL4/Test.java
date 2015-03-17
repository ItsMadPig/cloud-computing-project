package SQL4;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.util.List;
import java.util.ArrayList;


public class Test {
	
	public static void main(final String[] args) {
		final String teamInfo=getTeamInfo();
		Undertow server = Undertow.builder().addHttpListener(80, "0.0.0.0")
		//Undertow server = Undertow.builder().addHttpListener(8080, "0.0.0.0") //For local machine
				.setHandler(new HttpHandler() {

					public void handleRequest(final HttpServerExchange exchange)
							throws Exception {
						String response=null;
						if (exchange.isInIoThread()) {
              						exchange.dispatch(this);
              						return;
            					}
						String requestPath = exchange.getRequestPath();
						 if (requestPath.equals("/q2")) {
							 //long startTime = System.currentTimeMillis();
							 //System.out.println(Arrays.toString(args));
							 Q2 q2 = new Q2();
							response =q2.processRequest(exchange);
							 //long endTime   = System.currentTimeMillis();
							 //long totalTime = endTime - startTime;
							 //System.out.println("runtime: "+totalTime);
						
							 
						 }
						 else
						{
							System.out
									.println(exchange.getQueryString());
							
						}
						 exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
									"text/plain");
							exchange.getResponseSender().send(teamInfo+response);
					}
				}).build();
		server.start();

	}
	
	
	private static String getTeamInfo() {
		String teamID = "Oak";
		final String AWS_ACCOUNT_ID1 = "397168420013", // jiali
		AWS_ACCOUNT_ID2 = "779888392921", // ziyuan
		AWS_ACCOUNT_ID3 = "588767211863";// Aaron
		String response = String.format("%s,%s,%s,%s\n", teamID,
				AWS_ACCOUNT_ID1, AWS_ACCOUNT_ID2, AWS_ACCOUNT_ID3);
		return response;
		
	}

}

