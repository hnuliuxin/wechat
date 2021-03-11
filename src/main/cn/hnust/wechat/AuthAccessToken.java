package main.cn.hnust.wechat;

public class AuthAccessToken {
    //获取到的凭证
    private String accessToken;
    //凭证有效时间，单位：秒
    private int expiresin;
    private String refreshToken;
    private String openid;
    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getOpenid() {
        return refreshToken;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getRefreshToken() {
        return openid;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(int expiresin) {
        this.expiresin = expiresin;
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
