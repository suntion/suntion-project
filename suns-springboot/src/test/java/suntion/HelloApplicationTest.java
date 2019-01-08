package suntion;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import suns.Application;
import suns.controller.HelloController;

@RunWith(SpringJUnit4ClassRunner.class)
// 开启Web应用的配置， 用千模拟ServletContext
@WebAppConfiguration
// 指定启动类
@SpringBootTest(classes = Application.class)
public class HelloApplicationTest {

	private MockMvc mockMvc;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
	}

	@Test
	public void hello() {
		try {

			/**
			 * MockMvc对象： 用于模拟调用Controller的接口发起请求， 在@Test定义的hello 测试用例中， perform函数执行一次请求调用，
			 * accept用于执行接收的数据类型， andExpect用于判断接口返回的期望值。
			 */
			mockMvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isOk()).andExpect(content().string(equalTo("Hello World")));
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
