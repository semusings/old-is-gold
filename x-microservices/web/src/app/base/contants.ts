export class OmsConstants {
  public static get OMS(): string {
    return 'oms'
  };

  public static get LOGIN(): string {
    return 'login'
  };

  public static get BASE_PAGE_URI(): string {
    return '/' + OmsConstants.OMS
  };

  public static get LOGIN_PAGE_URI(): string {
    return OmsConstants.BASE_PAGE_URI + '/' + OmsConstants.LOGIN
  };

  public static get REGISTER(): string {
    return 'register'
  };

  public static get REGISTER_PAGE_URI(): string {
    return OmsConstants.BASE_PAGE_URI + '/' + OmsConstants.REGISTER
  };

}
