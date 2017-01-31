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

    //TODO Progress, time needed message and additional stuff

    @Override
    protected Task<String> createTask() {

        final StringBuilder output = new StringBuilder();
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

                // Search finished:

                if (status == RemoteBlastClient.Status.hitsFound) {
                    for (String line : remoteBlastClient.getRemoteAlignments()) {
                        output.append(line + "\n");
                    }
                }

                if (status == RemoteBlastClient.Status.noHitsFound) {
                    output.append("No hits found. Try again with new sequence or different settings");
                }

                else {
                    output.append("Blast Search failed. Check connection to server");
                }

                return  output.toString();
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
