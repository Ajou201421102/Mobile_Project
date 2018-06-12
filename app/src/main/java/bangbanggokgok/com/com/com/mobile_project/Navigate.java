package bangbanggokgok.com.com.com.mobile_project;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

public class Navigate extends Fragment implements mainMethod{
    TextView email;
    TextView name;
    ImageView close;
    FirebaseAuth mAuth;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mdatabaseReference;
    mainMethod mainMethod;
    String current_uid;
    ArrayList<String> info;
    View view;
    RecyclerView recyclerView;
    ArrayList<Informaion> Information = new ArrayList<>();

    ArrayList<NavigateItem> navigateItems = new ArrayList<>();
    ArrayList<String> GPS_x = new ArrayList<>();
    ArrayList<String> GPS_y = new ArrayList<>();
    ArrayList<String> endDateNode = new ArrayList<>();
    ArrayList<String> placeNode = new ArrayList<>();
    ArrayList<String> priceNode = new ArrayList<>();
    ArrayList<String> realmNameNode = new ArrayList<>();
    ArrayList<String> seqNode = new ArrayList<>();
    ArrayList<String> startDateNode = new ArrayList<>();
    ArrayList<String> titleNode = new ArrayList<>();
    ArrayList<String> urlList = new ArrayList<>();

    public void setListener(mainMethod mainMethod){
        this.mainMethod = mainMethod;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mdatabaseReference = mfirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        current_uid = mAuth.getCurrentUser().getUid().toString();
        view = inflater.inflate(R.layout.navigate,container,false);
        view.setClickable(true);
        email = view.findViewById(R.id.email_info);
        name = view.findViewById(R.id.name_info);
        close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMethod.CloseNavigate();
            }
        });
        mdatabaseReference.child("Users").child(current_uid).child("INFO").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        if(snapshot.getKey().equals("email"))
                            email.setText(snapshot.getValue().toString());
                        else
                            name.setText(snapshot.getValue().toString());
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        int num =2;
        mdatabaseReference.child("Users").child(current_uid).child("Seq").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GPS_x.clear();
                GPS_y.clear();
                endDateNode.clear();
                placeNode.clear();
                priceNode.clear();
                realmNameNode.clear();
                seqNode.clear();
                startDateNode.clear();
                titleNode.clear();
                urlList.clear();
                navigateItems.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    for (DataSnapshot children : dataSnapshot1.getChildren()) {
                        if (children.getKey().equals("GPS_x")) {
                            GPS_x.add(children.getValue().toString());
                        } else if ((children.getKey().equals("GPS_y"))) {
                            GPS_y.add(children.getValue().toString());
                        } else if ((children.getKey().equals("endDateNode"))) {
                            endDateNode.add(children.getValue().toString());
                        } else if ((children.getKey().equals("placeNode"))) {
                            placeNode.add(children.getValue().toString());
                        } else if ((children.getKey().equals("priceNode"))) {
                            priceNode.add(children.getValue().toString());
                        } else if ((children.getKey().equals("realmNameNode"))) {
                            realmNameNode.add(children.getValue().toString());
                        } else if ((children.getKey().equals("seqNode"))) {
                            seqNode.add(children.getValue().toString());
                        } else if ((children.getKey().equals("startDateNode"))) {
                            startDateNode.add(children.getValue().toString());
                        } else if ((children.getKey().equals("titleNode"))) {
                            titleNode.add(children.getValue().toString());
                        } else if ((children.getKey().equals("urlList"))) {
                            urlList.add(children.getValue().toString());
                        }

                    }
                }
                new UpdateNavigate().execute();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
    public class UpdateNavigate extends AsyncTask<String,Bitmap,Bitmap>{
        int point=0;
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            Bitmap resized = null;
            for(int i=0;i<urlList.size();i++){
                URL url = null;
                try {
                    url = new URL(urlList.get(i));
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
            navigateItems.add(new NavigateItem(seqNode.get(point),titleNode.get(point),bitmap[0],placeNode.get(point),realmNameNode.get(point),"~"+endDateNode.get(point)));
            point ++;
            //imageView.setImageBitmap(bitmap);

            super.onProgressUpdate(bitmap);
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            recyclerView = view.findViewById(R.id.navigateView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            final NavigateAdapter recyclerAdapter = new NavigateAdapter(navigateItems);
            recyclerAdapter.setListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    showDeleteDialog(position);
                    recyclerAdapter.notifyDataSetChanged();
                }
            });
            recyclerAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(recyclerAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), linearLayoutManager.getOrientation()));

            super.onPostExecute(bitmap);
        }


    }

    private void showDeleteDialog(int position) {
        String intent_seq = "";
        intent_seq += navigateItems.get(position).getSeq();
        Information = new ArrayList<>();
        new ApplyInfo().execute("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?ServiceKey=" +
                "qRzDzTz85rxbcjeZoCMhi739iMERvTiZzZcQhaREYzRN6IZhuv1Kv63NJYgkVEHBXxOa%2FSk%2FgeOPl%2FE4rujMFQ%3D%3D&seq="+intent_seq);
    }

    public class ApplyInfo extends AsyncTask<String, Document, Document> {

        private Document doc = null;
        private String urlList= "";
        private String titleNode = "";
        private String placeNode = "";
        private String realmNameNode ="";
        private String startDateNode = "";
        private String priceNode = "";
        private String endDateNode ="";
        private String GPS_x = "";
        private String GPS_y = "";
        private String seqNode = "";
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder(); //XML문서 빌더 객체를 생성
                doc = db.parse(new InputSource(url.openStream())); //XML문서를 파싱한다.
                doc.getDocumentElement().normalize();
                publishProgress(doc);
            } catch (Exception e) {
                e.printStackTrace();

            }
            return doc;
        }

        @Override
        protected void onProgressUpdate(Document... values) {
            String s = "";
            //data태그가 있는 노드를 찾아서 리스트 형태로 만들어서 반환

            NodeList nodeList = doc.getElementsByTagName("perforInfo");

            Node node = nodeList.item(0); //data엘리먼트 노드
            Element fstElmnt = (Element) node;

            NodeList nameList = fstElmnt.getElementsByTagName("seq");
            Element nameElement = (Element) nameList.item(0);
            nameList = nameElement.getChildNodes();
            seqNode += ((Node) nameList.item(0)).getNodeValue();

            NodeList imageList = fstElmnt.getElementsByTagName("imgUrl");
            Element imageElement = (Element) imageList.item(0);
            imageList = imageElement.getChildNodes();
            urlList += ((Node) imageList.item(0)).getNodeValue();

            NodeList titleList = fstElmnt.getElementsByTagName("title");
            Element titleElement = (Element) titleList.item(0);
            titleList = titleElement.getChildNodes();
            titleNode += ((Node) titleList.item(0)).getNodeValue();

            NodeList placeList = fstElmnt.getElementsByTagName("place");
            Element placeElement = (Element) placeList.item(0);
            placeList = placeElement.getChildNodes();
            placeNode += ((Node) placeList.item(0)).getNodeValue();

            NodeList priceList = fstElmnt.getElementsByTagName("price");
            Element priceElement = (Element) priceList.item(0);
            priceList = priceElement.getChildNodes();
            priceNode += ((Node) priceList.item(0)).getNodeValue();

            NodeList realmNameList = fstElmnt.getElementsByTagName("realmName");
            Element realmNameElement = (Element) realmNameList.item(0);
            realmNameList = realmNameElement.getChildNodes();
            realmNameNode += ((Node) realmNameList.item(0)).getNodeValue();

            NodeList startDateList = fstElmnt.getElementsByTagName("startDate");
            Element startElement = (Element) startDateList.item(0);
            startDateList = startElement.getChildNodes();
            startDateNode += ((Node) startDateList.item(0)).getNodeValue();

            NodeList endDateList = fstElmnt.getElementsByTagName("endDate");
            Element endDateElement = (Element) endDateList.item(0);
            endDateList = endDateElement.getChildNodes();
            endDateNode += ((Node) endDateList.item(0)).getNodeValue();
            try {
                NodeList GPS_X = fstElmnt.getElementsByTagName("gpsX");
                Element gps_x = (Element) GPS_X.item(0);
                GPS_X = gps_x.getChildNodes();
                GPS_x += ((Node) GPS_X.item(0)).getNodeValue();
            }catch (Exception e){
                GPS_x = "";
            }
            try {
                NodeList GPS_Y = fstElmnt.getElementsByTagName("gpsY");
                Element gps_y = (Element) GPS_Y.item(0);
                GPS_Y = gps_y.getChildNodes();
                GPS_y += ((Node) GPS_Y.item(0)).getNodeValue();
            }catch (Exception e){
                GPS_y = "";
            }
            Information.add(new Informaion(urlList,titleNode,placeNode,priceNode,realmNameNode,startDateNode,endDateNode,GPS_x,GPS_y,seqNode));
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Document document) {
            new doIntent().execute();
            super.onPostExecute(document);
        }




    }
    public class doIntent extends AsyncTask<String, Void, Void>{
        Intent intent;
        @Override
        protected Void doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            intent = new Intent(getActivity(),MapFragment.class);
            Log.d(Information.get(0).getTitleNode()," : 타이틀");
            intent.putExtra("imgUrl",Information.get(0).getUrlList());
            intent.putExtra("title", Information.get(0).getTitleNode());
            intent.putExtra("place",Information.get(0).getPlaceNode());
            intent.putExtra("price",Information.get(0).getPriceNode());
            intent.putExtra("realmName",Information.get(0).getRealmNameNode());
            intent.putExtra("startDate",Information.get(0).getStartDateNode());
            intent.putExtra("endDate",Information.get(0).getEndDateNode());
            intent.putExtra("gpsX",Information.get(0).getGPS_x());
            intent.putExtra("gpsY",Information.get(0).getGPS_y());
            intent.putExtra("seq",Information.get(0).getSeq());
            startActivity(intent);
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public void CloseNavigate() {

    }
}
