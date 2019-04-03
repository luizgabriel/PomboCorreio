package br.edu.ifce.pigeon.views;

import br.edu.ifce.pigeon.models.User;

public interface IUsersListener {
    void onAdded(int userId);

    void onRemoved(int userId);

    void onRefreshStatus(int userId, User.Status status, float percentage);
}
