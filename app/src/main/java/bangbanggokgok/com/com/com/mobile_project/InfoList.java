package bangbanggokgok.com.com.com.mobile_project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by User on 2018-06-07.
 */

public class InfoList extends Fragment {
    Activity root;
    String urlList= "";
    String seqNode = "";
    String titleNode = "";
    String placeNode = "";
    String realmNameNode ="";
    String endDateNode ="";


    RecyclerView recyclerView;
    ArrayList<RecyclerItem> recyclerItems;
    Document doc = null;
    TextView textview;
    ImageView imageView;
    int rows;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.infolist,container,false);

        root = getActivity();
        textview = view.findViewById(R.id.InfoList);
        imageView = view.findViewById(R.id.imageSample);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerItems = new ArrayList<>();


        new GetCount().execute("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/realm?ServiceKey=" +
                "qRzDzTz85rxbcjeZoCMhi739iMERvTiZzZcQhaREYzRN6IZhuv1Kv63NJYgkVEHBXxOa%2FSk%2FgeOPl%2FE4rujMFQ%3D%3D");
        new GetXMLTask().execute("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/realm?ServiceKey=" +
                "qRzDzTz85rxbcjeZoCMhi739iMERvTiZzZcQhaREYzRN6IZhuv1Kv63NJYgkVEHBXxOa%2FSk%2FgeOPl%2FE4rujMFQ%3D%3D"+"&rows="+rows);

        new DownloadImageTask().execute(0);

        return view;
    }

    private void showDeleteDialog(final int position) {
    }

    private class GetCount extends AsyncTask<String, Void, Document>{

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                //Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }
        @Override
        protected void onPostExecute(Document doc) {

            NodeList nodeList = doc.getElementsByTagName("msgBody");
            //data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환
            Node node = nodeList.item(0); //data엘리먼트 노드
            Element fstElmnt = (Element) node;
            NodeList nameList = fstElmnt.getElementsByTagName("totalCount");
            Element nameElement = (Element) nameList.item(0);
            nameList = nameElement.getChildNodes();
            rows = Integer.parseInt(((Node) nameList.item(0)).getNodeValue());
            super.onPostExecute(doc);
        }
    }
    private class GetXMLTask extends AsyncTask<String, Void, Document>{

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();

            } catch (Exception e) {
                //Toast.makeText(getBaseContext(), "Parsing Error", Toast.LENGTH_SHORT).show();
            }
            return doc;
        }

        @Override
        protected void onPostExecute(Document doc) {
            String s = "";
            //data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환

            NodeList nodeList = doc.getElementsByTagName("perforList");
            //data 태그를 가지는 노드를 찾음, 계층적인 노드 구조를 반환
            for (int i = 0; i < nodeList.getLength(); i++) {

                s += "" + i + "\n";
                Node node = nodeList.item(i); //data엘리먼트 노드
                Element fstElmnt = (Element) node;
                NodeList nameList = fstElmnt.getElementsByTagName("seq");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                s += "seq = " + ((Node) nameList.item(0)).getNodeValue() + "\n";
                seqNode += ((Node) nameList.item(0)).getNodeValue() + "\n";

                NodeList imageList = fstElmnt.getElementsByTagName("thumbnail");
                Element imageElement = (Element) imageList.item(0);
                imageList = imageElement.getChildNodes();
                s += "image = " + ((Node) imageList.item(0)).getNodeValue() + "\n";
                urlList += ((Node) imageList.item(0)).getNodeValue()+"\n";

                NodeList titleList = fstElmnt.getElementsByTagName("title");
                Element titleElement = (Element) titleList.item(0);
                titleList = titleElement.getChildNodes();
                s += "title = " + ((Node) titleList.item(0)).getNodeValue() + "\n";
                titleNode += ((Node) titleList.item(0)).getNodeValue() + "\n";

                NodeList placeList = fstElmnt.getElementsByTagName("place");
                Element placeElement = (Element) placeList.item(0);
                placeList = placeElement.getChildNodes();
                s += "place = " + ((Node) placeList.item(0)).getNodeValue() + "\n";
                placeNode += ((Node) placeList.item(0)).getNodeValue() + "\n";

                NodeList realmNameList = fstElmnt.getElementsByTagName("realmName");
                Element realmNameElement = (Element) realmNameList.item(0);
                realmNameList = realmNameElement.getChildNodes();
                s += "realmName = " + ((Node) realmNameList.item(0)).getNodeValue() + "\n";
                realmNameNode += ((Node) realmNameList.item(0)).getNodeValue() + "\n";

                NodeList endDateList = fstElmnt.getElementsByTagName("endDate");
                Element endDateElement = (Element) endDateList.item(0);
                endDateList = endDateElement.getChildNodes();
                s += "endDate = " + ((Node) endDateList.item(0)).getNodeValue() + "\n";
                endDateNode += ((Node) endDateList.item(0)).getNodeValue() + "\n";

                }

                //textview.setText(urlList);
            super.onPostExecute(doc);
        }
    }//end inner class - GetXMLTask
    private class DownloadImageTask extends AsyncTask<Integer, Bitmap, Void> {
        int point=0;
        String[] image;
        String[] seq;
        String[] title;
        String[] place;
        String[] realmName;
        String[] endDate;
        @Override
        protected Void doInBackground(Integer... position) {

            Bitmap bitmap = null;
            Bitmap resized = null;
            image = urlList.split("\n");
            seq = seqNode.split("\n");
            title = titleNode.split("\n");
            place = placeNode.split("\n");
            realmName = realmNameNode.split("\n");
            endDate = endDateNode.split("\n");
            for(int i=0;i<image.length;i++) {
                URL url = null;
                try {
                    url = new URL(image[i]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream in = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(in);
                    int height = bitmap.getHeight();
                    int width = bitmap.getWidth();
                    while (height > 118) {
                        resized = Bitmap.createScaledBitmap(bitmap, (width * 118) / height, 118, true);
                        height = resized.getHeight();
                        width = resized.getWidth();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                publishProgress(resized);
           }
            return null;
        }



        @Override
        protected void onProgressUpdate(Bitmap... bitmap) {
            recyclerItems.add(new RecyclerItem(seq[point],title[point],bitmap[0],place[point],realmName[point],"~"+endDate[point]));
            point ++;
            //imageView.setImageBitmap(bitmap);
            super.onProgressUpdate(bitmap[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(recyclerItems);

            recyclerAdapter.setListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    showDeleteDialog(position);
                    recyclerAdapter.notifyDataSetChanged();
                }
            });
            recyclerView.setAdapter(recyclerAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(root, linearLayoutManager.getOrientation()));
            super.onPostExecute(aVoid);
        }
    }
}


