public class SkiPass {
    String id;
    boolean blocked = false;
    Permissions permission;
    SkiPass(String id, Permissions permission) {
       this.id = id;
       this.permission = permission;
    }

   public boolean checkSkyPass(){
       return !blocked && permission.checkPermission();
   }

   public void useSkyPass() {
      if (checkSkyPass()) {
          permission.usePermission();
          System.out.println("SkiPass used");
      }
      else {
          System.out.println("SkyPass " +  id + " not permitted");
      }
   }
}

