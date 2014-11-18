package com.example.rhodymap;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 *	Customizes the rendering of the markers
 */
public class CustomClusterRenderer extends DefaultClusterRenderer<Item>
{	
	public CustomClusterRenderer(Context context, GoogleMap map,
			ClusterManager<Item> clusterManager) 
	{
		super(context, map, clusterManager);
	}
	
	protected void onBeforeClusterItemRendered(Item item, MarkerOptions mOptions) 
	{
        super.onBeforeClusterItemRendered(item, mOptions);

        mOptions.title(item.getTitle())
        		.snippet(item.getSnippet())
        		.icon(item.getIcon())
        		.position(item.getPosition());
    }
	
	protected boolean shouldRenderAsCluster(Cluster<Item> cluster) 
	{
        return cluster.getSize() > 3;
    }
	
}
