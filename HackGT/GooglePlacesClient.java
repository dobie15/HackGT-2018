package com.example.android.myapplication;

/**
 * Created by kelly on 10/20/2018.
 */

public class GooglePlacesClient
{
    private static final String GOOGLE_API_KEY  = "***";

    private final HttpClient    client          = new DefaultHttpClient();

    public static void main(final String[] args) throws ParseException, IOException, URISyntaxException
    {
        new GooglePlacesClient().performSearch("restaurant|cafe|bakery|shop|store|brewery|room|house|museum|park|commons", 8.6668310, 50.1093060);
        new GooglePlacesClient().performSearch("restaurant|cafe|bakery|shop|store|brewery|room|house|museum|park|commons", 43.769562, 11.255814);
    }

    public String performSearch(final String types, final double lon, final double lat) throws ParseException, IOException, URISyntaxException
    {
        final URIBuilder builder = new URIBuilder().setScheme("https").setHost("maps.googleapis.com").setPath("/maps/api/place/nearbysearch/json");

        builder.addParameter("location", lat + "," + lon);
        builder.addParameter("radius", "17000");
        builder.addParameter("types", types);
        builder.addParameter()
        builder.addParameter("key", GooglePlacesClient.GOOGLE_API_KEY);

        final HttpUriRequest request = new HttpGet(builder.build());

        final HttpResponse execute = this.client.execute(request);

        final String response = EntityUtils.toString(execute.getEntity());

        System.out.println(response);
        return response;
    }

}
