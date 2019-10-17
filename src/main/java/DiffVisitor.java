import org.repodriller.domain.Commit;
import org.repodriller.domain.Modification;
import org.repodriller.domain.ModificationType;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.RepositoryFile;
import org.repodriller.scm.SCMRepository;

import java.util.List;

public class DiffVisitor implements CommitVisitor {

    private List<RepositoryFile> mOldFiles;

    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {

        if (mOldFiles != null) {
            for (Modification modification : commit.getModifications()) {
                if (modification.getType() == ModificationType.MODIFY) {
                    for (RepositoryFile oldFile : mOldFiles) {

                        if (oldFile.getFile().getName().equals(modification.getFileName())) {

                            String oldSource = oldFile.getSourceCode();
                            String newSource = modification.getSourceCode();

                            List<Changes> chaneList = MethodDiff.diffMethodsParameters(
                                    oldSource,
                                    newSource);

                            for (Changes change : chaneList) {
                                writer.write(commit.getHash(), modification.getFileName(), change.getOldMethod(),
                                        change.getNewMethod());
                            }
                        }
                    }
                }
            }
        }
        repo.getScm().checkout(commit.getHash());
        mOldFiles = repo.getScm().files();
    }
}
