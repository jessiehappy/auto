package auto.datamodel;

/**
 * 认证状态   0：未认证   1：审核中   2：失败,未通过   3：成功,已通过
 */
public enum AuthStatus {

    UNKNOWN, AUDIT, AUDIT_FAILURE, AUDIT_PASSED;

}
