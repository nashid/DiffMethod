import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.commit.OnlyModificationsWithFileTypes;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.CollectConfiguration;
import org.repodriller.scm.GitRemoteRepository;
import org.repodriller.scm.SCMRepository;

import java.util.Arrays;

public class CustomStudy implements Study {

    private String mUrl;
    private String mCloneFolder;
    private String mOutputName;

    public CustomStudy(String url, String cloneFolder, String outputName) {
        mUrl = url;
        mCloneFolder = cloneFolder;
        mOutputName = outputName;
    }

    @Override
    public void execute() {

        SCMRepository scmRepository = GitRemoteRepository
                .hostedOn(mUrl)
                .inTempDir(mCloneFolder)
                .buildAsSCMRepository();

        CSVFile csvFile = new CSVFile(mOutputName);
        csvFile.write("Commit SHA", "File Name", "Old Function Signature", "New Function Signature");

        new RepositoryMining()
                .in(scmRepository)
                .filters(new OnlyModificationsWithFileTypes(Arrays.asList(".java")))
                .through(Commits.all())
                .collect(new CollectConfiguration().commitMessages())
                .collect(new CollectConfiguration().sourceCode())
                .process(new DiffVisitor(), csvFile)
                .mine();
    }
}
