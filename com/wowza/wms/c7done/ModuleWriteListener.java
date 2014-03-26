import java.io.*; 
import java.util.*;

import com.wowza.wms.application.*;
import com.wowza.wms.module.*;
import com.wowza.wms.stream.*;

public class ModuleWriteListener extends ModuleBase
{
  class WriteListener implements IMediaWriterActionNotify
  {
    public void onFLVAddMetadata(IMediaStream stream, Map<String, Object> extraMetadata)
    {
      getLogger().info("ModuleWriteListener.onFLVAddMetadata["+stream.getContextStr()+"]");
    }

    public void onWriteComplete(IMediaStream stream, File file)
    {
      getLogger().info("VM7DONE-ModuleWriteListener.onWriteComplete["+stream.getContextStr()+"]: "+file);
      String full_path = file.getAbsolutePath() + ".done";
      try{
        File done_file = new File(full_path);
        FileOutputStream t = new FileOutputStream(done_file);
        t.write(file.getName().getBytes());
        t.close();  
      }catch(Exception ex){
        getLogger().info("VM7DONE-ModuleWriteListener.onWriteComplete["+stream.getContextStr()+"]: "+file);
        getLogger().info("VM7DONE-ModuleWriteListener.onWriteComplete["+stream.getContextStr()+"]: exception \n"+ex.getMessage());
        ex.printStackTrace();
      }
      

      getLogger().info("VM7DONE-ModuleWriteListener.onWriteComplete["+stream.getContextStr()+"]: wrote .done :"+full_path);
    }
  }
  
  public void onAppStart(IApplicationInstance appInstance)
  {
    appInstance.addMediaWriterListener(new WriteListener());
  }
}