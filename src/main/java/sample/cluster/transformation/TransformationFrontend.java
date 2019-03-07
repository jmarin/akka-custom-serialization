package sample.cluster.transformation;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Terminated;
import sample.cluster.transformation.TransformationMessages.*;

import java.util.ArrayList;
import java.util.List;

import static sample.cluster.transformation.TransformationMessages.BACKEND_REGISTRATION;

public class TransformationFrontend extends AbstractActor {

    List<ActorRef> backends = new ArrayList<>();
    int jobCounter = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TransformationJob.class, job -> backends.isEmpty(), job -> {
                    sender().tell(new JobFailed("Service Unavailable, try again later"),
                            sender());
                })
                .match(TransformationJob.class, job -> {
                    jobCounter ++;
                    backends.get(jobCounter % backends.size())
                            .forward(job, getContext());
                })
                .matchEquals(BACKEND_REGISTRATION, message -> {
                    getContext().watch(sender());
                    backends.add(sender());
                })
                .match(Terminated.class, terminated -> {
                    backends.remove(terminated.getActor());
                })
                .build();
    }
}
