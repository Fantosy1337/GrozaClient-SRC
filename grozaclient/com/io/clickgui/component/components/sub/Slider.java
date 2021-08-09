/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 */
package grozaclient.com.io.clickgui.component.components.sub;

import grozaclient.com.io.clickgui.component.Component;
import grozaclient.com.io.clickgui.component.components.Button;
import grozaclient.com.io.settings.Settinga;
import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Slider
extends Component {
    private boolean hovered;
    private Settinga set;
    private Button parent;
    private int offset;
    private int x;
    private int y;
    private boolean dragging = false;
    private double renderWidth;

    public Slider(Settinga value, Button button, int offset) {
        this.set = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        Gui.func_73734_a((int)(this.parent.parent.getX() + 2), (int)(this.parent.parent.getY() + this.offset), (int)(this.parent.parent.getX() + this.parent.parent.getWidth()), (int)(this.parent.parent.getY() + this.offset + 12), (int)(this.hovered ? -14540254 : -15658735));
        int drag = (int)(this.set.getValDouble() / this.set.getMax() * (double)this.parent.parent.getWidth());
        Gui.func_73734_a((int)(this.parent.parent.getX() + 2), (int)(this.parent.parent.getY() + this.offset), (int)(this.parent.parent.getX() + (int)this.renderWidth), (int)(this.parent.parent.getY() + this.offset + 12), (int)(this.hovered ? -11184811 : -12303292));
        Gui.func_73734_a((int)this.parent.parent.getX(), (int)(this.parent.parent.getY() + this.offset), (int)(this.parent.parent.getX() + 2), (int)(this.parent.parent.getY() + this.offset + 12), (int)-15658735);
        Minecraft.func_71410_x().field_71466_p.func_175063_a(this.set.getName() + ": " + this.set.getValDouble(), (float)(this.parent.parent.getX() + 15), (float)(this.parent.parent.getY() + this.offset + 2), -1);
    }

    @Override
    public void setOff(int newOff) {
        this.offset = newOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButtonD(mouseX, mouseY) || this.isMouseOnButtonI(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
        double diff = Math.min(88, Math.max(0, mouseX - this.x));
        double min = this.set.getMin();
        double max = this.set.getMax();
        this.renderWidth = 88.0 * (this.set.getValDouble() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0.0) {
                this.set.setValDouble(this.set.getMin());
            } else {
                double newValue = Slider.roundToPlace(diff / 88.0 * (max - min) + min, 2);
                this.set.setValDouble(newValue);
            }
        }
    }

    private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.dragging = true;
        }
        if (this.isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
    }

    public boolean isMouseOnButtonD(int x, int y) {
        return x > this.x && x < this.x + (this.parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 12;
    }

    public boolean isMouseOnButtonI(int x, int y) {
        return x > this.x + this.parent.parent.getWidth() / 2 && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 12;
    }
}

