import java.util.Arrays;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;


public class Test {

	public static void main(final String[] args) {
		Undertow server = Undertow.builder().addHttpListener(8080, "0.0.0.0")
				.setHandler(new HttpHandler() {

					public void handleRequest(final HttpServerExchange exchange)
							throws Exception {
						String requestPath = exchange.getRequestPath();
						if (requestPath.equals("/q1")) {
							Q1 q1 = new Q1();
							q1.processRequest(exchange);
						}
						else 
						 if (requestPath.equals("/q2")) {
							 if(args.length<1 || args[0]=="")
							    {
							        System.out.println("Proper Usage is: java Test <DNS name of mysql server>");
							        System.exit(0);
							    }
							 long startTime = System.currentTimeMillis();
							 System.out.println(Arrays.toString(args));
							 Q2 q2 = new Q2(args[0]);
							 q2.processRequest(exchange);
							 long endTime   = System.currentTimeMillis();
							 long totalTime = endTime - startTime;
							 System.out.println("runtime: "+totalTime);
							 
						 }
						 else
						{
							System.out
									.println("Waiting for another correct url request");
							exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
									"text/plain");
							exchange.getResponseSender().send("I got your data.:D");
							
						}
					}
				}).build();
		server.start();

	}

}