public class SkiPass {
    static String id;
    public boolean blocked = false;
    static Permissions permission;
    public SkiPass(String id, Permissions permission) {
       SkiPass.id = id;
       SkiPass.permission = permission;
    }
   public boolean checkSkyPass(){
       return permission.checkPermission();
   };

   public void useSkyPass() {
      if (permission.checkPermission()) {
         System.out.println("SkiPass used");
      }
      else {
         System.out.println("SkyPass " +  id + " not permitted");
      }
      permission.usePermission();
   };


}

