/*
reference: http://www.rgagnon.com/javadetails/java-0490.html
*/
package utilities;

import java.util.*;
import java.io.*;


public abstract class CheckLogChange extends TimerTask {
  private long lastModifiedDate;
  private File logFile;

  public CheckLogChange( String fileName ) {
    this.logFile = new File(fileName);
    this.lastModifiedDate = logFile.lastModified();
  }

  public final void run() {
    long lastModifiedDate = logFile.lastModified();

    if( this.lastModifiedDate != lastModifiedDate ) {
      this.lastModifiedDate = lastModifiedDate;
      updateLog(logFile);
    }
  }

  protected abstract void updateLog( File file );
}