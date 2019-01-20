package com.example.heidrun.bak_project_learnquest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DBConnector  {

    private static String BASE_URL = "http://learnquest.devity.net";

    private static String URL_GET_SUBJECTS = BASE_URL + "/get_subjects.php";
    private static String URL_GET_USERS = BASE_URL + "/get_users.php";
    private static String URL_GET_QUESTIONS = BASE_URL + "/get_questions.php";
    private static String URL_GET_THEMES = BASE_URL + "/get_themes.php";
    private static String URL_GET_PROGRESS = BASE_URL + "/get_progress.php";
    private static String URL_GET_ANSWERSINGLE = BASE_URL + "/get_questions_single.php";
    private static String URL_GET_ANSWERMULTI = BASE_URL + "/get_questions_multi.php";

    public static ArrayList<Subject> getSubjects() {
        ArrayList<Subject> subjects = new ArrayList<>();
        try{
            JSONObject object = getJSONfromURL(URL_GET_SUBJECTS);
            JSONArray objects = object.getJSONArray("subjects");
            for(int i = 0; i < objects.length(); i++)
            {
                JSONObject o = objects.getJSONObject(i);
                int id = o.getInt("Fach_ID");
                String name = o.getString("Fach");
                Subject subject = new Subject(id, name);
                subjects.add(subject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public static ArrayList<Theme> getThemes() {
        ArrayList<Theme> themes = new ArrayList<>();
        try{
            JSONObject object = getJSONfromURL(URL_GET_THEMES);
            JSONArray objects = object.getJSONArray("themes");
            for(int i = 0; i < objects.length(); i++)
            {
                JSONObject o = objects.getJSONObject(i);
                int id = o.getInt("T_ID");
                String name = o.getString("Theme");
                Theme theme = new Theme(id, name);
                themes.add(theme);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return themes;
    }

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try{
            JSONObject object = getJSONfromURL(URL_GET_USERS);
            JSONArray objects = object.getJSONArray("users");
            for(int i = 0; i < objects.length(); i++)
            {
                JSONObject o = objects.getJSONObject(i);
                int id = o.getInt("U_ID");
                String name = o.getString("Name");
                String email = o.getString("Email");
                String avatar = o.getString("Avatar");
                User user = new User(id, name, email, avatar);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        try{
            JSONObject object = getJSONfromURL(URL_GET_QUESTIONS);
            JSONArray objects = object.getJSONArray("questions");
            for(int i = 0; i < objects.length(); i++)
            {
                JSONObject o = objects.getJSONObject(i);
                int id = o.getInt("F_ID");
                int tid = o.getInt("T_ID");
                int fid = o.getInt("Fach_ID");
                String frage = o.getString("Frage");
                boolean fragenart = o.getBoolean("Fragenart");
                String Antwort1 = o.getString("Antwort1");
                String Antwort2 = o.getString("Antwort2");
                String Antwort3 = o.getString("Antwort3");
                String Antwort4 = o.getString("Antwort4");
                Question question = new Question(id, tid, fid, frage, fragenart, Antwort1,
                        Antwort2, Antwort3, Antwort4);
                questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static ArrayList<Progress> getProgress() {
        ArrayList<Progress> progress = new ArrayList<>();
        try{
            JSONObject object = getJSONfromURL(URL_GET_PROGRESS);
            JSONArray objects = object.getJSONArray("progress");
            for(int i = 0; i < objects.length(); i++)
            {
                JSONObject o = objects.getJSONObject(i);
                int ID = o.getInt("ID");
                int U_ID = o.getInt("U_ID");
                int Themen_gesamt = o.getInt("Themen_gesamt");
                int Fragen_gesamt = o.getInt("Fragen_gesamt");
                int Fragen_richtig = o.getInt("Fragen_richtig");
                int Fragen_falsch = o.getInt("Fragen_falsch");
                int Facher_gesamt = o.getInt("Facher_gesamt");
                int Faecher_user = o.getInt("Faecher_user");
                int Themen_user = o.getInt("Themen_user");
                Progress prog = new Progress(ID, U_ID, Themen_gesamt, Fragen_gesamt, Fragen_richtig,
                        Fragen_falsch, Facher_gesamt, Faecher_user, Themen_user);
                progress.add(prog);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return progress;
    }

    public static ArrayList<AnswerMulti> getAnswerMulti() {
        ArrayList<AnswerMulti> answers = new ArrayList<>();
        try{
            JSONObject object = getJSONfromURL(URL_GET_ANSWERMULTI);
            JSONArray objects = object.getJSONArray("questions_multi");
            for(int i = 0; i < objects.length(); i++)
            {
                JSONObject o = objects.getJSONObject(i);
                int fid = o.getInt("F_ID");
                int id = o.getInt("ID");
                String Antwort1 = o.getString("Loesung1");
                String Antwort2 = o.getString("Loesung2");
                String Antwort3 = o.getString("Loesung3");
                String Antwort4 = o.getString("Loesung4");
                AnswerMulti answer = new AnswerMulti(fid, id, Antwort1,
                        Antwort2, Antwort3, Antwort4);
                answers.add(answer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return answers;
    }

    public static ArrayList<AnswerSingle> getAnswerSingle() {
        ArrayList<AnswerSingle> answers = new ArrayList<>();
        try{
            JSONObject object = getJSONfromURL(URL_GET_ANSWERSINGLE);
            JSONArray objects = object.getJSONArray("questions_single");
            for(int i = 0; i < objects.length(); i++)
            {
                JSONObject o = objects.getJSONObject(i);
                int fid = o.getInt("F_ID");
                int id = o.getInt("ID");
                String Antwort1 = o.getString("Loesung1");
                AnswerSingle answer = new AnswerSingle(fid, id, Antwort1);
                answers.add(answer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return answers;
    }


    public static JSONObject getJSONfromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }


}
