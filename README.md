# NewsApp

# Libraries And Technologies used

- Clean Architecture: Data, Domain, Presentation.
- Use Case: For business logic of project.
- Mapper: For separate entities.
- MVVM + StateFlow : Android architecture used to separate logic code from ui and save the
  application state in case the configuration changes.
- Retrofit + Gson Converter: Fetch news from rest api as a gson file and convert it to a kotlin object.
- Room : Save,delete and get the articles into a local database.
- RecyclerView with DiffUtil.
- Coroutines : Executing some code in the background.
- Dagger hilt : Dependency injection.
- Navigation Component : Navigate between fragments.
- Glide : Catch and cache images from the internet and show them in an imageView.
- viewBinding : to access the views without needing to inflate them.
