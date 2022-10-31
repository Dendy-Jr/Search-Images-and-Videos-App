package ui.dendi.finder.core.core.widget

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.NavigationRes
import androidx.annotation.StringRes
import androidx.core.view.*
import ui.dendi.finder.core.R
import ui.dendi.finder.core.core.models.MainTab
import ui.dendi.finder.core.databinding.ViewMainTabbarItemBinding

private const val ITEM_ANIMATION_DURATION = 100L
private const val SELECTION_ANIMATION_DURATION = 300L

class MainTabBarLayout(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    fun interface OnTabSelectedListener {
        fun onTabSelected(tab: MainTabBarItem)
    }

    private val inflater = LayoutInflater.from(context)

    private var tabsCount = -1
    private var selectedTab: MainTabBarItem? = null
    private var listener: OnTabSelectedListener? = null

    private val bindings: Sequence<ViewMainTabbarItemBinding>
        get() = children.filter { it != selectionView }.map { ViewMainTabbarItemBinding.bind(it) }
    private var selectedItemWidth = 0
    private var deselectedItemWidth = 0
    private val itemHeight: Int
        get() = resources.getDimensionPixelSize(R.dimen.mainTabBarItemHeight)

    private val selectionView = View(context).apply {
        setBackgroundResource(R.drawable.bg_main_tabbar_selection)
    }

    init {
        layoutTransition = LayoutTransition().apply {
            setDuration(ITEM_ANIMATION_DURATION)
        }
        setBackgroundResource(R.drawable.bg_main_tabbar)
        resources.getDimensionPixelSize(R.dimen.mainTabBarPaddingHorizontal).let {
            updatePadding(left = it, right = it)
        }

        addView(selectionView)
    }

    fun setTabs(tabs: List<MainTabBarItem>, selection: MainTabBarItem) {
        children.forEach { if (it != selectionView) removeView(it) }
        tabsCount = tabs.size

        val selectedItemsWidth = mutableListOf<Int>()
        val deselectedItemsWidth = mutableListOf<Int>()
        tabs.forEach { tab ->
            val binding = inflateTab(tab, isSelected = true)
            val spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)

            // Measure width of selected tabs
            binding.root.measure(spec, spec)
            selectedItemsWidth.add(binding.root.measuredWidth)

            // Measure width of deselected tabs
            binding.updateSelection(isSelected = false)
            binding.root.measure(spec, spec)
            deselectedItemsWidth.add(binding.root.measuredWidth)

            // Inflate tab item
            addView(binding.root)
        }
        selectedItemWidth = selectedItemsWidth.maxByOrNull { it } ?: 0
        deselectedItemWidth = deselectedItemsWidth.maxByOrNull { it } ?: 0

        selectionView.updateLayoutParams {
            width = selectedItemWidth
            height = itemHeight
        }
        doOnNextLayout {
            selectionView.translationY = (height - itemHeight) / 2f
        }

        selectTab(selection)
    }

    private fun selectTab(tab: MainTabBarItem) {
        val firstSelection = selectedTab == null
        selectedTab = tab
        bindings.forEach {
            it.updateSelection(isSelected = it.tab == selectedTab)
        }
        doOnNextLayout {
            bindings.find { it.tab == selectedTab }?.let { binding ->
                val translationX = binding.root.x
                if (firstSelection) {
                    selectionView.translationX = translationX
                    listener?.onTabSelected(tab)
                } else {
                    selectionView.animate()
                        .setDuration(SELECTION_ANIMATION_DURATION)
                        .translationX(translationX)
                        .withEndAction { listener?.onTabSelected(tab) }
                        .start()
                }
            } ?: run { listener?.onTabSelected(tab) }
        }
    }

    fun onTabSelected(listener: OnTabSelectedListener) {
        this.listener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            measuredWidth,
            MeasureSpec.makeMeasureSpec(
                resources.getDimensionPixelSize(R.dimen.mainTabBarHeight),
                MeasureSpec.EXACTLY
            )
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (tabsCount <= 0) return

        selectionView.layout(0, 0, selectionView.measuredWidth, selectionView.measuredHeight)

        var x = paddingStart
        val contentWidth = (tabsCount - 1) * deselectedItemWidth + selectedItemWidth
        val spacing = (measuredWidth - paddingStart - paddingEnd - contentWidth) / (tabsCount - 1)

        bindings.forEach { binding ->
            val tab = binding.tab
            val isSelected = tab == selectedTab

            val width = if (isSelected) selectedItemWidth else deselectedItemWidth
            val height = itemHeight

            val left = x
            val top = (measuredHeight - height) / 2
            val right = left + width
            val bottom = top + height
            binding.root.layout(left, top, right, bottom)

            x += width + spacing
        }
    }

    private fun inflateTab(tab: MainTabBarItem, isSelected: Boolean): ViewMainTabbarItemBinding =
        ViewMainTabbarItemBinding.inflate(inflater, this, false).apply {
            this.tab = tab
            root.layoutTransition = LayoutTransition().apply {
                setDuration(ITEM_ANIMATION_DURATION)
            }
            updateSelection(isSelected)

            root.setOnClickListener {
                selectTab(tab)
            }
        }

    private inline var ViewMainTabbarItemBinding.tab: MainTabBarItem
        set(value) {
            root.setTag(R.id.mainTabLayoutTag, value)
        }
        get() = root.getTag(R.id.mainTabLayoutTag) as MainTabBarItem

    private fun ViewMainTabbarItemBinding.updateSelection(isSelected: Boolean) {
        val tab = tab

        ivIcon.isSelected = isSelected
        tvTitle.isVisible = isSelected

        val horizontalPadding = if (isSelected) {
            resources.getDimensionPixelSize(R.dimen.mainTabBarItemPaddingHorizontalSelected)
        } else {
            resources.getDimensionPixelSize(R.dimen.mainTabBarItemPaddingHorizontal)
        }
        root.updatePadding(left = horizontalPadding, right = horizontalPadding)

        ivIcon.setImageResource(tab.iconRes)
        tvTitle.setText(tab.titleRes)
    }
}

data class MainTabBarItem(
    @NavigationRes val graphId: Int,
    @DrawableRes val iconRes: Int,
    @StringRes val titleRes: Int,
    val origin: MainTab,
)