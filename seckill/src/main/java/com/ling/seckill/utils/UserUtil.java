package com.ling.seckill.utils;/**
 * Created by Ky2Fe on 2021/7/31 17:45
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ling.seckill.pojo.User;
import com.ling.seckill.vo.Result;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Ky2Fe
 * @program: seckill
 * @description: 生成用户
 **/

public class UserUtil {
    private static void createUser(int count) throws Exception {
        List<User> users=new ArrayList<>(count);
        for (int i=0;i<count;i++){
            User user = new User();
            user.setId(18000000000L+i);
            user.setNickname("user"+i);
            user.setPassword(MD5Util.inputPassToDBPass("123456","1a2b3c4d"));
            user.setSalt("1a2b3c4d");
            users.add(user);
        }
        System.out.println("create user");
        //插入数据库
//        Connection conn=getConn();
//        String sql="insert into t_user(id,nickname,password,salt) values(?,?,?,?)";
//        PreparedStatement pstm = conn.prepareStatement(sql);
//        for (User user:users){
//            pstm.setLong(1,user.getId());
//            pstm.setString(2,user.getNickname());
//            pstm.setString(3, user.getPassword());
//            pstm.setString(4, user.getSalt());
//            pstm.addBatch();
//        }
//        pstm.executeBatch();
//        pstm.close();
//        conn.close();
//        System.out.println("insert to db");
        //登录，生成令牌
        String loginUrl="http://localhost:8080/login/doLogin";
        File file = new File("D:\\Project\\JAVA\\秒杀系统\\jmeter\\config.txt");
        if (file.exists()){
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        //读写起始位置
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            URL url = new URL(loginUrl);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFormPass("123456");
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
            ObjectMapper mapper = new ObjectMapper();
            Result result = mapper.readValue(response, Result.class);
            String userTicket = ((String) result.getObj());
            System.out.println("create userTicket : " + user.getId());

            String row = user.getId() + "," + userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getId());
        }
        raf.close();
    }

    private static Connection getConn() throws Exception {
        String url="jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username="root";
        String password="1234";
        String driver="com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url,username,password);

    }

    public static void main(String[] args) throws Exception {
        createUser(5000);
    }
}
