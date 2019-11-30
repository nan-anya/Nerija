package com.example.nerija;

import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SearchManager extends Thread
{
    ArrayList<String> time;
    String depPlaceId, arrPlaceId, depPlanDate;
    private boolean setData = false;
    private int searchMode = 0;
    public SearchManager(String depPlaceId, String arrPlaceId, String depPlanDate, ArrayList<String> time, int searchMode){
        this.arrPlaceId = arrPlaceId;
        this.depPlanDate = depPlanDate;
        this.depPlaceId = depPlaceId;
        this.time = time;
        this.searchMode = searchMode;
    }

    public void run ()
    {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.tago.go.kr/openapi/service/TrainInfoService/getStrtpntAlocFndTrainInfo"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=JCcukRNndBkWeWSDRt0YGJXn1N3lLY%2FoI%2BY2KP6HiInZaFQNEVK7zHTJpgIjtEDOerAnA2InZYQSN0fVgqruSg%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
            urlBuilder.append("&" + URLEncoder.encode("depPlaceId", "UTF-8") + "=" + URLEncoder.encode(depPlaceId, "UTF-8")); /*출발기차역ID*/
            urlBuilder.append("&" + URLEncoder.encode("arrPlaceId", "UTF-8") + "=" + URLEncoder.encode(arrPlaceId, "UTF-8")); /*도착기차역ID*/
            urlBuilder.append("&" + URLEncoder.encode("depPlandTime", "UTF-8") + "=" + URLEncoder.encode(depPlanDate, "UTF-8")); /*출발일*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            //System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            Log.d("확인",sb.toString());

            JSONParser parser = new JSONParser();

            Object obj = parser.parse(sb.toString());

            JSONObject parse_response = (JSONObject) ((JSONObject) obj).get("response");/*Top level인 response 키로 데이터 1차 파싱*/
            JSONObject parse_body = (JSONObject) parse_response.get("body");
            JSONObject parse_items = (JSONObject) parse_body.get("items");
            /*item으로 부터 itemlist받아오기*/
            JSONArray parse_item = (JSONArray) parse_items.get("item");
            JSONObject data;
            String depplantime;//출발시간
            String arrplantime;//도착시간
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMDD");
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
            SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
            String currentDate = dateFormat.format(cal.getTime());//현재 날짜
            String currentHour = hourFormat.format(cal.getTime());//현재 시
            String currentMinute = minuteFormat.format(cal.getTime());//현재 분
            int curD = Integer.parseInt(currentDate);
            int curH = Integer.parseInt(currentHour);
            int curM = Integer.parseInt(currentMinute);

            for (int j = 0; j < parse_item.size(); j++) {

                data = (JSONObject) parse_item.get(j);/*parse_item이라는 array에서 i번째 index에 존재하는 data를  받아오기*/
                depplantime = data.get("depplandtime").toString();
                arrplantime = data.get("arrplandtime").toString();
                int depD = Integer.parseInt(depplantime.substring(0, 8));
                int depH = Integer.parseInt(depplantime.substring(8,10));
                int depM = Integer.parseInt(depplantime.substring(10,12));
                //시,분,초로 고치기 두개 스트링 붙이기 (출발시간~ 도착시간)
                if(curD >= depD)
                {
                    if (curH < depH)
                    {
                        time.add(editDate(depplantime, arrplantime));
                    }

                    else if (curH == depH && curM < depM)
                    {
                        time.add(editDate(depplantime, arrplantime));
                    }
                }
            }
            rd.close();
            conn.disconnect();


        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        setData =true;
    }

    public boolean isReady()
    {
        return setData;
    }//데이터 다 들어올때가지 메인 대기를 위해 사용

    public String editDate(String dep, String arr){//time 값 형태 전환
        String time;
        dep = dep.substring(8,12);
        arr = arr.substring(8,12);
        dep = dep.substring(0,2)+" : "+dep.substring(2,4);
        arr = arr.substring(0,2)+" : "+arr.substring(2,4);
        time = dep.concat(" ~ "+arr);

        return time;
    }
}
