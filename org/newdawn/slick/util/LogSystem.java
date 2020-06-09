package org.newdawn.slick.util;

public interface LogSystem {
  void error(String paramString, Throwable paramThrowable);
  
  void error(Throwable paramThrowable);
  
  void error(String paramString);
  
  void warn(String paramString);
  
  void warn(String paramString, Throwable paramThrowable);
  
  void info(String paramString);
  
  void debug(String paramString);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slic\\util\LogSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */