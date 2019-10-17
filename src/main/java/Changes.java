public class Changes {

    private String mOldMethod;
    private String mNewMethod;

    public Changes(String oldMethod, String newMethod) {
        mOldMethod = oldMethod;
        mNewMethod = newMethod;
    }

    public String getOldMethod() {
        return mOldMethod;
    }

    public String getNewMethod() {
        return mNewMethod;
    }
}
