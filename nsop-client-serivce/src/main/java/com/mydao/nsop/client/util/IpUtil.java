package com.mydao.nsop.client.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class IpUtil {

        public static boolean pingServer(String server, int timeout)
        {
            BufferedReader in = null;
            Runtime r = Runtime.getRuntime();

            String pingCommand = "ping " + server + " -n 1 -w " + timeout;
            try
            {
                Process p = r.exec(pingCommand);
                if (p == null)
                {
                    return false;
                }
                in = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
                String line = null;
                while ((line = in.readLine()) != null)
                {
                    if (line.contains("time=")||line.contains("时间=")||line.contains("Time="))
                    {
                        return true;
                    }
                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return false;
            }
            finally
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return false;
        }

}
