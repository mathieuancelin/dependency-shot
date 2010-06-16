package cx.ath.mancel01.dependencyshot.dsl;

/**
 * A mock service.
 * 
 * @author Mathieu ANCELIN
 */
public class BasicMockService implements BasicService {
    /**
     * A boolean.
     */
    private boolean gone = false;

    @Override
    public void go() {
        gone = true;
    }

    @Override
    public boolean isGone() {
        return gone;
    }

    @Override
    public String getString() {
        return "the returned mock string";
    }

    @Override
    public int getInt() {
        return 0;
    }

    @Override
    public void setString(final String str) {
        System.out.println("set string mock " + str);
    }

}
