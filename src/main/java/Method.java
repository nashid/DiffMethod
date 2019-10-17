import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;

public class Method {

    private String mName;
    private String mSignature;
    private String mNameWithoutArgs;
    private NodeList<Parameter> mNodeList;

    public Method(String name, String nameWithoutArgs, String signature, NodeList<Parameter> nodeList) {
        mName = name;
        mSignature = signature;
        mNodeList = nodeList;
        mNameWithoutArgs = nameWithoutArgs;
    }

    public String getName() {
        return mName;
    }

    public String getSignature() {
        return mSignature;
    }

    public String getNameWithoutArgs() {
        return mNameWithoutArgs;
    }

    public NodeList<Parameter> getNodeList() {
        return mNodeList;
    }
}
