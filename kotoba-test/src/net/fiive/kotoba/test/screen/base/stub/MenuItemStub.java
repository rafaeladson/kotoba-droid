package net.fiive.kotoba.test.screen.base.stub;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class MenuItemStub implements MenuItem {
	private int menuItemId;

	public MenuItemStub(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	@Override
	public int getItemId() {
		return menuItemId;
	}

	@Override
	public int getGroupId() {
		return 0;
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public MenuItem setTitle(CharSequence charSequence) {
		return null;
	}

	@Override
	public MenuItem setTitle(int i) {
		return null;
	}

	@Override
	public CharSequence getTitle() {
		return null;
	}

	@Override
	public MenuItem setTitleCondensed(CharSequence charSequence) {
		return null;
	}

	@Override
	public CharSequence getTitleCondensed() {
		return null;
	}

	@Override
	public MenuItem setIcon(Drawable drawable) {
		return null;
	}

	@Override
	public MenuItem setIcon(int i) {
		return null;
	}

	@Override
	public Drawable getIcon() {
		return null;
	}

	@Override
	public MenuItem setIntent(Intent intent) {
		return null;
	}

	@Override
	public Intent getIntent() {
		return null;
	}

	@Override
	public MenuItem setShortcut(char c, char c1) {
		return null;
	}

	@Override
	public MenuItem setNumericShortcut(char c) {
		return null;
	}

	@Override
	public char getNumericShortcut() {
		return 0;
	}

	@Override
	public MenuItem setAlphabeticShortcut(char c) {
		return null;
	}

	@Override
	public char getAlphabeticShortcut() {
		return 0;
	}

	@Override
	public MenuItem setCheckable(boolean b) {
		return null;
	}

	@Override
	public boolean isCheckable() {
		return false;
	}

	@Override
	public MenuItem setChecked(boolean b) {
		return null;
	}

	@Override
	public boolean isChecked() {
		return false;
	}

	@Override
	public MenuItem setVisible(boolean b) {
		return null;
	}

	@Override
	public boolean isVisible() {
		return false;
	}

	@Override
	public MenuItem setEnabled(boolean b) {
		return null;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public boolean hasSubMenu() {
		return false;
	}

	@Override
	public SubMenu getSubMenu() {
		return null;
	}

	@Override
	public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
		return null;
	}

	@Override
	public ContextMenu.ContextMenuInfo getMenuInfo() {
		return null;
	}

	@Override
	public void setShowAsAction(int i) {

	}

	@Override
	public MenuItem setActionView(View view) {
		return null;
	}

	@Override
	public MenuItem setActionView(int i) {
		return null;
	}

	@Override
	public View getActionView() {
		return null;
	}
}
