git filter-branch -f --commit-filter '
        if [ "$GIT_COMMITTER_NAME" = "Ignacio Crespo" ];
        then
                GIT_COMMITTER_NAME="ignaciotcrespo";
                GIT_AUTHOR_NAME="ignaciotcrespo";
                GIT_COMMITTER_EMAIL="itcrespo@gmail.com";
                GIT_AUTHOR_EMAIL="itcrespo@gmail.com";
                git commit-tree "$@";
        else
                git commit-tree "$@";
        fi' HEAD

# git push origin -f

