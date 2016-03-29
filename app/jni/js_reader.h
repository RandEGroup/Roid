#include <iostream>
#include <string>
#include <vector>
#include <fstream>

typedef std::string STRING;
typedef std::string::iterator STRING_ITER;

//分析js文件
class JS_Reader
{
	public:
	JS_Reader();
	JS_Reader(STRING filename);
	void ReadFile(STRING filename);//读取文件
	void Parse();//分析代码


	private:
	
	STRING m_Content; //文件内容
	STRING_ITER m_Iter; //字串符迭代器
	STRING_ITER m_Begin,m_End;//迭代器收尾
	std::fstream m_Fin; //文件流
	
	char GetNextChar();//读取下个字符
	void IgnoreSpaces();//跳过空格
	void ResetIterator();//重置迭代器
	
	class Token
	{
		public:
		Token();
		int index = 0;
		STRING_ITER itor;
	};
	
	class Scope
	{
		public:
		Scope();
		int start;
		int end;
	};
	
};