# AdapterDelegateCards
<img src="https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/feed.gif" width="360">

*Update 2021: See [JetpackComposeCards](https://github.com/vrickey123/JetpackComposeCards) for a Jetpack Compose implementation of the same design system.*

A proof of concept for a **composition-over-inheritance RecyclerView that uses Card Components and JSON-driven styles**. It's built with Hannes Dorfmann's library of the same name.

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

```kotlin
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

In this sense, **an Adapater Delegate is a Card Component**.

#### Material Card Adapter Delegate
```kotlin
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
```kotlin
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
```kotlin
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
```kotlin
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
Layout is best conceptualized as the abstract positioning of views in a container. Our layouts are concerned with what views go where without knowing what our final card will look like.

1. Material Card Layout
2. Thumbnail Card Layout
3. Visual Card Layout

#### Material Card Layout
![Layout Material](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/layout_material.png)
- 3:2 Image
- Overline Text View
- Title Text View
- Body Text View

#### Thumbnail Card Layout
![Layout Thumbnail](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/layout_thumbnail.png)
- 1:1 Image
- Overline Text View
- Title Text View
- Body Text View

#### Visual Card Layout
![Layout Visual](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/layout_visual.png)
- 4:2 Image
- Overlay Gradient
- Overlay Title Text View

### Styles
The styles are concerned with the relationship between views in a layout; i.e. margin, padding, or line height properties. By keep text styles in separate dimension, styles can be simplified and reused across many layouts easily.

```xml
<style name="CustomComponent.Overline">
        <item name="android:layout_marginTop">@dimen/material_component_vertical_padding</item>
    </style>

    <style name="CustomComponent.Headline">
        <item name="android:layout_marginTop">@dimen/material_component_vertical_padding</item>
    </style>

    <style name="CustomComponent.Headline.Overlay">
        <item name="android:layout_marginBottom">16dp</item>
    </style>

    <style name="CustomComponent.Body">
        <item name="android:layout_marginTop">@dimen/material_component_vertical_padding</item>
        <item name="lastBaselineToBottomHeight">20dp</item>
    </style>
```

### Text Styles
The text styles make up our base Typography stylesheet and override [Android's TextAppearance attributes](https://developer.android.com/reference/android/R.styleable.html#TextAppearance).

```xml
    <style name="Body" parent="TextAppearance.MaterialComponents.Body1">
        <item name="android:textColor">?android:textColorSecondary</item>
    </style>

    <style name="Body.Inverse" parent="Body">
        <item name="android:textColor">@android:color/white</item>
    </style>

    <style name="Body.Sans" parent="Body">
        <item name="fontFamily">@font/quattrocentosans_regular</item>
    </style>

    <style name="Body.Serif" parent="Body">
        <item name="fontFamily">@font/merriweather_regular</item>
    </style>
```

#### Composite Text Styles
Using our text styles and typography style sheet, we can also define a set of composite text styles (pairings of multiple fonts, colors, ect.).

##### Material
![Text Style Material](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/text_style_material.png)

##### Header
![Text Style Header](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/text_style_header.png)

##### Italic
![Text Style Italic](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/text_style_italic.png)

##### Light
![Text Style Light](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/text_style_light.png)

##### Typeset
![Text Style Typeset](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/text_style_typeset.png)

##### Visual
![Text Style Typeset](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/text_style_visual.png)

## Composition with a JSON Model
### FeedItem.json
Using a `layoutKey` and a `textStyleKey`, we can compose our cards on the fly with a combination layout and composite text style.

```json
{
    "id": 1,
    "layoutKey": "material",
    "textStyleKey": "italic",
    "title": "Lorem ipsum",
    "body": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    "overline": "Birds of Paradise"
  }
```

### Layout: AdapterDelegate#isForViewType
The `isForViewType` method defines the condition when we would like to use a specific card layout.
```kotlin
override fun isForViewType(items: List<FeedItem>, position: Int): Boolean {
        return items[position].layoutKey == "visual"
    }
```

### Text Style: CardViewHolder#setCompositeTextAppearance
```kotlin
fun setCompositeTextAppearance(bodyTextView: TextView?, titleTextView: TextView, textStyleKey: String) {
        when (textStyleKey) {
            "material" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body) }
            }
            "light" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5_Serif_Light)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body_Sans) }
            }
            "visual" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H6_Sans_Bold_Inverse)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body_Sans) }
            }
            "typeset" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5_Serif)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body_Serif) }
            }
            "header" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5_Sans_Bold)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body_Sans) }
            }
            "italic" -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5_SerifItalic)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body_Serif) }
            }
            else -> {
                TextViewCompat.setTextAppearance(titleTextView, R.style.H5)
                bodyTextView?.let { TextViewCompat.setTextAppearance(it, R.style.Body) }
            }
        }
    }
```

### Examples
#### Basil Theme x Matieral Card Layout x Italic Text
![Material-Italic](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/card_material.png)

#### Basil Theme x Thumbnail Card Layout x Light Text
![Thumbnail-Light](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/card_thumbnail.png)

#### Basil Theme x Visual Card Layout x Visual Text
![Visual-Visual](https://github.com/vrickey123/AdapterDelegateCards/blob/develop/docs/card_visual.png)

## Resources
- [Adapter Delegates](https://github.com/sockeqwe/AdapterDelegates)
- [Material Typography](https://material.io/design/typography/the-type-system.html#type-scale)
- [Material Basil Theme](https://material.io/design/material-studies/basil.html#color)
- [Android Text Appearance Attributes](https://developer.android.com/reference/android/R.styleable.html#TextAppearance)
- [Google Fonts](https://design.google/library/google-fonts/)

