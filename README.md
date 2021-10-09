# RichLokalТекстLib

RichLocalText is a library that allows modders and pack-makers to use translated rich text.  
Easily add font styles and colors anywhere without code! 

# Installation
Drop the .jar into the /mods folder, and enjoy rich localization text.  
Requires no other dependencies.

\*_resourcepack utilizing the_ **Rich**™ Lokal™ ***Текст***™ _Capabilities™ not included._

# Usage
Use /tellraw-like format in language files.  
When overriding a string that has format placeholders use {"index":n} instead;

Example, changing the default chat text format:
```json
//en_us.json
{
  "chat.type.text": 
  [
    {"text":"A mysterious stranger known only as <", "color": "blue"},
    {"index":0, "obfuscated": true},
    {"text":"> uttered: \"", "color": "blue"},
    {"index":1},
    {"text":"\"", "color": "blue"}
  ]
}
```
