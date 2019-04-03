package br.edu.ifce.pigeon.views;

public interface IRefreshStatus {
    enum Status {
        SLEEPING,
        WRITING
    }

    void onRefresh(Status status);
}
