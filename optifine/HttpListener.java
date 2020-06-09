package optifine;

public interface HttpListener {
  void finished(HttpRequest paramHttpRequest, HttpResponse paramHttpResponse);
  
  void failed(HttpRequest paramHttpRequest, Exception paramException);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\HttpListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */