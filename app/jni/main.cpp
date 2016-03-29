#include <iostream>
#include "js_reader.h"

#define PATH "/sdcard/target"
void START()
{
	JS_Reader* reader = new JS_Reader(PATH);
	
	reader->Parse();
}