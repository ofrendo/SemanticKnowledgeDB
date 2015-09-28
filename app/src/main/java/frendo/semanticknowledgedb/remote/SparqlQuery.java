package frendo.semanticknowledgedb.remote;

import android.os.AsyncTask;

/**
 * Created by Oliver on 26.09.2015.
 */
public class SparqlQuery extends AsyncTask<Object, Integer, String> {

    private QueryHandler queryHandler;
    private Callback callback;

    public SparqlQuery() {
        this.queryHandler = new QueryHandler();
    }

    protected String doInBackground(Object... params) {
        String sparql = (String) params[0];
        this.callback = (Callback) params[1];

        String result = queryHandler.performPostCall(sparql);
        return result;
    }
    protected void onPostExecute(String result) {
        callback.call(result);
    }

}
