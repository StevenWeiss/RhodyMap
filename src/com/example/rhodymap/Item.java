package com.example.rhodymap;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 *	The marker
 */
public class Item extends Point implements ClusterItem
{
	private String title;
	private String snippet;
	private BitmapDescriptor icon;
	
	public Item(String t, String s, BitmapDescriptor i)
	{
		title = t;
		snippet = s;
		icon = i;
	}
	
	public BitmapDescriptor getIcon()
	{
		return icon;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getSnippet()
	{
		return snippet;
	}
}
