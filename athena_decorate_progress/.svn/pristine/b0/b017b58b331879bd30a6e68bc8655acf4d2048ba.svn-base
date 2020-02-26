package test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dec.pro.entity.user.User;
import com.dec.pro.util.Page;
import com.dec.pro.util.UUid;

/**
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>succez</p>
 * @author ZhangJinjin
 * @createdate 2018年5月4日
 */
public class intToHex_Alter {

    /**
     * 这次算法用了StringBuffer效率更好
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

       // System.out.println("请输入要转换的十进制的数：");
       // Scanner input = new Scanner(System.in);
       //int n = input.nextInt();        
       // System.out.println("转换的十六进制的数为："+intToHex(n));
    	try {
    		Page<User> p=new Page<User>();
    		List<User> l=new ArrayList<>();
    		User u=new User();
    		User u1=new User();
    		u.setId(UUid.getUUid());
    		u.setUsername("测试呀");
    		u.setCreated(new Date());
    		l.add(u);
    		l.add(u1);
    		p.setOrderBy("created");
    		p.setPageNo(11);
    		p.setTotalItems(100);
    		p.setResult(l);
			testReflect(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static String intToHex(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;            
        }
        a = s.reverse().toString();
        return a;
    }
  //java中遍历实体类，获取属性名和属性值
    public static void testReflect(Object model) throws Exception{
    	Map<String,Object> map = new HashMap<>();
    	map.put("s", "sssss");
    	System.out.print("实体类=="+model.getClass().getName());
    	System.out.println(" 查询条件为："+map);
        for (Field field : model.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println(field.getName() + ":" + field.get(model) );
            }
    }
}