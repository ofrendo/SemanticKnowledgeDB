package frendo.semanticknowledgedb.remote;

import android.util.Log;

/**
 * Created by Oliver on 26.09.2015.
 */
public class SparqlHandler {


    public void SparqlHandler() {

    }

    public void testConnection() {
        String sparql =
                "SELECT * WHERE { " +
                        "   ?subject a karate:kategorie ." +
                        "   ?subject rdfs:label ?label ." +
                        "   ?subject rdfs:comment ?comment ." +
                        "} ";
        executeQuery(sparql, new Callback() {
            public void call(String result) {
                Log.i("SparqlHandler", "testConnection: " + result);
            }
        });
    }

    public void loadKategorien(Callback callback) {
        String sparql =
                "SELECT * WHERE {" +
                "    karate:kategorie karate:abfolge/rdf:rest*/rdf:first ?id ." +
                "    ?id rdfs:label ?label. " +
                "} ";
        executeQuery(sparql, callback);
    }

    public void loadList(String containerID, Callback callback) {
        String sparql =
                "SELECT ?id ?label ?icon ?links (EXISTS{?id karate:abfolge []} AS ?hasAbfolge) WHERE {" +
                        "    <" + containerID + "> karate:abfolge/rdf:rest*/rdf:first ?id ." +
                        "    ?id rdfs:label ?label. " +
                        "    OPTIONAL { ?id karate:icon ?icon } . " +
                        "    OPTIONAL { ?id karate:videos ?links }" +
                        "} ";
        executeQuery(sparql, callback);
    }

    public void loadItem(String itemID, Callback callback) {
        String sparql =
                "SELECT * WHERE {" +
                        "    <" + itemID + "> rdfs:label ?label ." +
                        "    OPTIONAL { <" + itemID + "> rdfs:comment ?comment } ." +
                        "    OPTIONAL { <" + itemID + "> karate:videos ?videos } ." +
                        "    OPTIONAL { <" + itemID + "> karate:images ?images } ." +
                        "} ";
        executeQuery(sparql, callback);
    }

    private void executeQuery(String sparql, Callback callback) {
        new SparqlQuery().execute(sparql, callback);
    }

}
