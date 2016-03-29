#include "js_reader.h"

using namespace std;

JS_Reader::JS_Reader()
{
}

JS_Reader::JS_Reader(STRING filename)
{
	ReadFile(filename);
}
void JS_Reader::ReadFile(STRING filename)
{
	m_Fin.open(filename, ios::in);
	
	STRING tmp;
	while(getline(m_Fin, tmp))
	{
		m_Content+=tmp+"\n";
	}
	
	m_Begin = m_Content.begin();
	m_End = m_Content.end();
	
	ResetIterator();
	
	
}
void JS_Reader::Parse()
{
	IgnoreSpaces();
	cout<<GetNextChar()<<endl;
}

char JS_Reader::GetNextChar()
{
	if (m_Iter != m_End)
	{
		return *(m_Iter++);
	}
	else
	{
		return *(m_Iter);
	}
}
void JS_Reader::IgnoreSpaces()
{
	while(m_Iter != m_End)
	{
		if (*m_Iter == ' ' || *m_Iter == '\n' || *m_Iter == '\t' || *m_Iter == '\r')
		{
			++m_Iter;
		}
		else
		{
			break;
		}
	}
}
void JS_Reader::ResetIterator()
{
	m_Iter = m_Begin;
}