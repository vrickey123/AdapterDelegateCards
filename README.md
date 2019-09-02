# AdapterDelegateCards
[Adapter Delegates](https://github.com/sockeqwe/AdapterDelegates) are an inversion control pattern for the RecylerView designed to help you _add reusable cards to a list_ instead of _defining a fix set of cards to an Adapter_ that [leads to a subclass hell](http://hannesdorfmann.com/android/adapter-delegates).

This sample uses Adapter Delegates to create a visually robust feed with a few goals:

1. Composition over Inheritance in a ReyclerView
2. Separate Layout, Style, and Text Style
3. Compose card style with a JSON model

Following these principles, we will get 15 different card variants with just three Adapter Delegates.

## RecyclerView Adapter vs Adapter Delegates
The main objective of the Adapter Delegate system is to favor composition over inheritence when using the RecyclerView. **We will create Cards instead of Adapters.**

### RecyclerView Adapter
The standard RecyclerView Adapter's performance is great, but it can quickly grow unweildy if your Design team wants a feed with more than just a standard material card. 

In this example, there are three cards (View Types) that require multiple when statements across `onCreateViewHolder`, `onBindViewHolder`, and `getItemViewType`.

Even worse, you have to make sure your when case in `onCreateViewHolder` matches the case in `onBindViewHolder` otherwise you might end up with a mismatched implementation.

This solution doesn't scale well when we have 10 different View Types with unique on bind logic for each case.

```
class FeedAdapter : ListAdapter<FeedItem, CardViewHolder>(FeedItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_MATERIAL_CARD = 1
        const val VIEW_TYPE_THUMBNAIL_CARD = 2
        const val VIEW_TYPE_VISUAL_CARD = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_MATERIAL_CARD -> CardViewHolder(inflater.inflate(R.layout.card_material, parent, false))
            VIEW_TYPE_THUMBNAIL_CARD -> CardViewHolder(inflater.inflate(R.layout.card_thumbnail, parent, false))
            VIEW_TYPE_VISUAL_CARD -> CardViewHolder(inflater.inflate(R.layout.card_visual, parent, false))
            else -> CardViewHolder(inflater.inflate(R.layout.card_material, parent, false))
        }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        return when (viewType) {
            VIEW_TYPE_MATERIAL_CARD -> // Do something custom for the Material View Type
            VIEW_TYPE_THUMBNAIL_CARD -> // Do something custom for the Thumbnail View Type
            VIEW_TYPE_VISUAL_CARD -> // // Do something custom for Visual View Type
            else -> // Define your fallback case
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).layoutKey) {
            "material" -> VIEW_TYPE_MATERIAL_CARD
            "thumbnail" -> VIEW_TYPE_THUMBNAIL_CARD
            "visual" -> VIEW_TYPE_VISUAL_CARD
            else -> VIEW_TYPE_MATERIAL_CARD
        }
    }
}
```

### Adapter Delegates
Ideally, we want to harness the performance of the RecyclerView Adapter with something that scales well when we have 10 or more cards in our feed. 

Each Adapter Delegate implements the RecyclerView Adapter methods of `onCreateViewHolder` and `onBindViewHolder` to compose a card. 

#### Material Card Adapter Delegate
```
class MaterialCardAdapterDelegate() : AbstractCardAdapterDelegate() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_material, parent, false))
    }

    override fun isForViewType(items: List<FeedItem>, position: Int): Boolean {
        return items[position].layoutKey == "material"
    }

    override fun onBindViewHolder(
        items: List<FeedItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(items, position, holder, payloads)
    }
}
```

#### Thumbnail Card Adapter Delegate
```
class ThumbnailCardAdapterDelegate() : AbstractCardAdapterDelegate() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_thumbnail, parent, false))
    }

    override fun isForViewType(items: List<FeedItem>, position: Int): Boolean {
        return items[position].layoutKey == "thumbnail"
    }

    override fun onBindViewHolder(
        items: List<FeedItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(items, position, holder, payloads)
    }
}
```

#### Visual Card Adapter Delegate
```
class VisualCardAdapterDelegate() : AbstractCardAdapterDelegate() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_visual, parent, false))
    }

    override fun isForViewType(items: List<FeedItem>, position: Int): Boolean {
        return items[position].layoutKey == "visual"
    }

    override fun onBindViewHolder(
        items: List<FeedItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(items, position, holder, payloads)
    }
}
```

#### Adapter Delegate Manager
```
class FeedAdapterDelegatesManager() : ListDelegationAdapter<List<FeedItem>>() {

    companion object {
        const val VIEW_TYPE_MATERIAL_CARD = 1
        const val VIEW_TYPE_THUMBNAIL_CARD = 2
        const val VIEW_TYPE_VISUAL_CARD = 3
    }

    init {
        delegatesManager.addDelegate(VIEW_TYPE_MATERIAL_CARD,
            MaterialCardAdapterDelegate()
        )
        delegatesManager.addDelegate(VIEW_TYPE_THUMBNAIL_CARD,
            ThumbnailCardAdapterDelegate()
        )
        delegatesManager.addDelegate(VIEW_TYPE_VISUAL_CARD,
            VisualCardAdapterDelegate()
        )
        delegatesManager.fallbackDelegate =
            MaterialCardAdapterDelegate()
    }
}
```

### Performance Optimiziations towards 60 FPS
By using `CardViewHolder` and resetting its default values, we can even reuse inflated views across multiple _View Types_ (thumbnail vs. visual) to achieve faster scroll performance.

## Separating Layout, Style, and Text Style
By separating layout, styles, and text styles across three dimensions; it is possible to create 15 card variants with just three RecyclerView Adapter Delegates.

### Layout
### Styles
### Text Styles

## Composite Text
