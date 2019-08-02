package com.shtick.utils.scratch3.runner.standard.bundle;

import java.util.Hashtable;
import java.util.LinkedList;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.shtick.utils.scratch3.runner.core.FeatureSetGenerator;
import com.shtick.utils.scratch3.runner.standard.StandardFeatureGenerator;

/**
 **/
public class Activator implements BundleActivator {
	private LinkedList<ServiceRegistration<?>> runtimeRegistrations=new LinkedList<>();
	
    /**
     * Implements BundleActivator.start(). Prints
     * a message and adds itself to the bundle context as a service
     * listener.
     * @param context the framework context for the bundle.
     **/
    @Override
	public void start(BundleContext context){
		System.out.println(this.getClass().getCanonicalName()+": Starting.");
		synchronized(runtimeRegistrations) {
			runtimeRegistrations.add(context.registerService(FeatureSetGenerator.class.getName(), new StandardFeatureGenerator(),new Hashtable<String, String>()));
		}
    }

    /**
     * Implements BundleActivator.stop(). Prints
     * a message and removes itself from the bundle context as a
     * service listener.
     * @param context the framework context for the bundle.
     **/
    @Override
	public void stop(BundleContext context){
		System.out.println(this.getClass().getCanonicalName()+": Stopping.");
		synchronized(runtimeRegistrations) {
			for(ServiceRegistration<?> runtimeRegistration:runtimeRegistrations)
				runtimeRegistration.unregister();
			runtimeRegistrations.clear();
		}
    }

}