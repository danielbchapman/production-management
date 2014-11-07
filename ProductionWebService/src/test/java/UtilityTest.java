import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.danielbchapman.production.Utility;
import com.danielbchapman.production.entity.Hotel;
import com.google.gson.Gson;


public class UtilityTest
{
	public static void main(String[] args)
	{
		ArrayList<Mock> mocks = new ArrayList<Mock>();
		for(int i = 0; i < 10; i++)
			mocks.add(new Mock(i + " a", i + " b"));
		
		Mock[] mockArray = Utility.array(mocks, Mock.class);
		for(Mock m : mockArray)
			System.out.println(m);
		
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		for(int i = 0; i < 5; i++)
			hotels.add(new Hotel(i + " Hotel", i + " notes"));
		
		System.out.println(new Gson().toJson(Utility.array(hotels, Hotel.class)));
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Mock
	{
		String a;
		String b;
	}
}
