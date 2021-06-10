package de.dhbw.binaeratops.service.mychat;

/**
 * @author Lukas Göpel
 * Date: 10.06.2021
 * Time: 09:32
 */
public interface ActiveUserChangeListener {

    /**
     * call when Observable's internal state is changed.
     */
    void notifyActiveUserChange();
}
