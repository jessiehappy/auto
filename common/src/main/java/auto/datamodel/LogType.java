package auto.datamodel;


public enum LogType {
    //user common
    register, login, grant, verifyCode, grantPassword, resetPassword, bindTelephone, bindOauthPlatform, getInfo,
    reddie, supportCenterView, supportCenterSubmit, updateUser, verify,
    
    //system
    push, pushMsg;
    
    public static boolean isUserAction(LogType type) {
        if (type == push || type == pushMsg || type.name().startsWith("admin")) return false;
        return true;
    }
    
    public static boolean isAdminAction(LogType type) {
        return type.name().startsWith("admin");
    }
}
