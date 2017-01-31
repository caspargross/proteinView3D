package blast;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.swing.plaf.basic.BasicTreeUI;

/**
 * Created by Caspar on 30.01.2017.
 */
public class BlastService extends Service<String>{



    private String querySequence;
    private String dataBase;
    private RemoteBlastClient.BlastProgram blastProgram;
    private RemoteBlastClient.Status status;
    public Property<String> statusProperty = new SimpleObjectProperty<>();



    @Override
    protected Task<String> createTask() {
        // Task class
        return new Task<String>() {
            @Override
                protected String call() throws Exception {

                statusProperty.setValue("started");
                // Initialize remoteBlastClient with settings from GUI
                final RemoteBlastClient remoteBlastClient = new RemoteBlastClient();
                remoteBlastClient.setDatabase(dataBase);
                remoteBlastClient.setProgram(blastProgram);
                remoteBlastClient.startRemoteSearch(querySequence);

                System.out.println("BLAST SEARCH STARTED");
                // Use simple status checking method from code sample
                status = null;
                do {
                    if (status != null) {Thread.sleep(5000);}
                    status = remoteBlastClient.getRemoteStatus();
                    statusProperty.setValue(status.toString());
                } while (status == RemoteBlastClient.Status.searching);

                return "";
            }
        };
    }

    public String getBlastStatus(){
        return  status.toString();
    }


    public void setQuerySequence(String querySequence) {
        this.querySequence = querySequence;
    }

    public String getQuerySequence() {
        return querySequence;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public RemoteBlastClient.BlastProgram getBlastProgram() {
        return blastProgram;
    }

    public void setBlastProgram(RemoteBlastClient.BlastProgram blastProgram) {
        this.blastProgram = blastProgram;
    }
}
