# Utils

add this one your application

```
Helper.initialize(this, new String[]{"YOUR PACKAGE NAME PRODUCTION", "YOUR PACKAGE NAME DEBUG"});
Helper.setConfig(new Config());
```

add this one first

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

for using my library

```
implementation 'com.github.fajaranugrah:Utils:version'
```
